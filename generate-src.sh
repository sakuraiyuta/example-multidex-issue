#!/bin/bash

find -name func*.clj | xargs rm

for ((i=0; i < 3000; i++)); do
	echo "(ns com.example.multidex-issue.func${i} (:require [neko.notify :refer [toast]])) (defn func${i} [] (toast \"call func${i}\"))" \
		> src/clojure/com/example/multidex_issue/func${i}.clj
done

