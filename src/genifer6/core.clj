(ns genifer6.core
  (:require [clara.rules :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (println "Welcome to Genifer 6"))

(defrecord Happy [name level])

(defrecord Loves [name1 name2])

(defrule rule1
  [Happy (= :high level)]
  =>
  (println "High level happy detected!"))

(defrule rule2
  [Happy (= ?name1 name)]
  [Loves (= ?name1 name1) (= ?name2 name2)]
  =>
  (println "Notify" ?name1 "that"
           ?name2 "is happy!"))

(-> (mk-session 'genifer6.core)
    (insert (->Loves "John" "Mary")
            (->Happy "John" :high))
    (fire-rules))
