(ns genifer6.core
  (:require [clara.rules :refer :all])
  (:require [genifer6.GeneticAlgorithmTest1 :refer :all])
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

(defrule rule3
  [Loves (= ?name1 name1) (= ?name2 name2)]
  [Loves (= ?name2 name1) (= ?name1 name2)]
  =>
  (insert! (->Happy ?name1 :high)))

(-> (mk-session 'genifer6.core)
    (insert (->Loves "John" "Mary")
            (->Loves "Mary" "John")
            (->Happy "Mary" :high))
    (fire-rules))


;; General form of a "predicate" declaration:
(defrecord P0001 [V0001 V0002 V0003])

;; General form of a "rule" declaration:
;(defrule R0001
;  [P0001 (= ?
;  =>
;  [])
