(ns genifer6.toClara
  (:require [clara.rules :refer :all])
  (:gen-class))

;; General form of a "predicate" declaration
;; =========================================
;; * The format is generic and does not require learning, can be fixed initially
; (defrecord p1 [L1 L2 L3])

;; General form of a "rule" declaration
;; ====================================
; (defrule r1
;  [p1 (= ?v1 L1) (= ?v2 L2)]
;  [p1 (= ?v2 L1) (= ?v1 L2)]
;  =>
;  (insert! (->p3 ?v1)))
;; The gene would be:
;; p1 ?v1 ?v2 ^ p1 ?v2 ?v1 => p3 ?v1
;; The format as list:
;; [ [] [] ] => []

; Declare predicates
(defrecord p1 [L1 L2 L3])
(defrecord p2 [L1 L2 L3])
(defrecord p3 [L1 L2 L3])
(defrecord p4 [L1 L2 L3])
(defrecord p5 [L1 L2 L3])
(defrecord p6 [L1 L2 L3])
(defrecord p7 [L1 L2 L3])
(defrecord p8 [L1 L2 L3])
(defrecord p9 [L1 L2 L3])
(defrecord p10 [L1 L2 L3])

(defn prepareClara []

  (-> (mk-session 'genifer6.toClara)
    (insert (->p1 "John" "Mary")
            (->p2 "Mary" "John")
            (->p3 "Mary"))
    (fire-rules)))
