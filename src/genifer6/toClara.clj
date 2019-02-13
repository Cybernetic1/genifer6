(ns genifer6.toClara
  (:require [clara.rules :refer :all])
  (:gen-class))

(defrecord p1 [x1 x2 x3])
(defrecord p2 [x1 x2 x3])
(defrecord p3 [x1 x2 x3])
(defrecord p4 [x1 x2 x3])
(defrecord p5 [x1 x2 x3])
(defrecord p6 [x1 x2 x3])
(defrecord p7 [x1 x2 x3])
(defrecord p8 [x1 x2 x3])
(defrecord p9 [x1 x2 x3])
(defrecord p10 [x1 x2 x3])

(-> (mk-session 'genifer6.core)
    (insert (->Loves "John" "Mary")
            (->Loves "Mary" "John")
            (->Happy "Mary" :high))
    (fire-rules))

;; General form of a "predicate" declaration
;; =========================================
;(defrecord P0001 [V0001 V0002 V0003])

;; General form of a "rule" declaration
;; ====================================
;(defrule R0001
;  [P0001 (= ?
;  =>
;  [])

(defn prepareClara []
  ;; Declare predicates
  (dotimes genifer6.GeneticAlgorithm/numPreds
    (defrecord (symbol 

  (-> (mk-session 'Genifer)
    (insert (->Loves "John" "Mary")
            (->Loves "Mary" "John")
            (->Happy "Mary" :high))
    (fire-rules))

  (defrecord P.. [L.. ])

  )
