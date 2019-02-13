(ns genifer6.core
  (:require [clara.rules :refer :all])
  (:require [genifer6.GeneticAlgorithmTest1 :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (println "Welcome to Genifer 6"))

;; In the long term, the rules engine needs to be rewritten.

(defrecord Happy [name])

(defrecord Loves [name1 name2])

(defrule rule1
  [Happy (= ?name name)]
  =>
  (println ?name "is happy!"))

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
  (insert! (->Happy ?name1)))

(-> (mk-session 'genifer6.core)
    (insert (->Loves "John" "Mary")
            (->Loves "Mary" "John")
            (->Happy "Mary"))
    (fire-rules))

;; General form of a "predicate" declaration:
;; * The format is generic and does not require learning, can be fixed initially
(defrecord P0001 [L1-P0001 L2-P0001 L3-P0001])

;; General form of a "rule" declaration:
(defrule R0001
  [P0001 (= ?V0001 L1-P0001) (= ?V0002 L2-P0001)]
  [P0001 (= ?V0002 L1-P0001) (= ?V0001 L2-P0001)]
  =>
  (insert! (->P0003 ?V0001)))
;; The gene would be:
;; P1 ?V1 ?V2 ^ P1 ?V2 ?V1 => P3 ?V1
;; The format as list:
;; [ [] [] ] => []
