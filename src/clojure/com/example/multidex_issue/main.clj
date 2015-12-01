(ns com.example.multidex-issue.main
  (:require [neko.activity :refer [defactivity set-content-view!]]
            [neko.debug :refer [*a]]
            [neko.notify :refer [toast]]
            [neko.resource :as res]
            [neko.find-view :refer [find-view]]
            [neko.threading :refer [on-ui]])
  (:import android.widget.EditText))

(res/import-all)

(defn notify-from-edit
  [activity]
  (let [^EditText input (.getText (find-view activity ::user-input))
        ns-sym (->> input
                    str
                    read-string
                    (str "com.miraimaker.classloader-issue.func")
                    symbol)
        fn-sym (->> input
                    str
                    read-string
                    (str ns-sym "/func")
                    symbol)
        func (do
               (require ns-sym)
               (eval fn-sym))]
    (func)))

(defactivity com.example.multidex_issue.MainActivity
  :key :main

  (onCreate [this bundle]
            (.superOnCreate this bundle)
            (neko.debug/keep-screen-on this)
            (on-ui
              (set-content-view! (*a)
                                 [:linear-layout {:orientation :vertical
                                                  :layout-width :fill
                                                  :layout-height :wrap}
                                  [:edit-text {:id ::user-input
                                               :hint "Type text here"
                                               :layout-width :fill}]
                                  [:button {:text R$string/touch_me ;; We use resource here, but could
                                            ;; have used a plain string too.
                                            :on-click (fn [_] (notify-from-edit (*a)))}]]))))
