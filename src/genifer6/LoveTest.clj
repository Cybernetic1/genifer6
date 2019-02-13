(ns genifer6.LoveTest
  (:require [clara.rules :refer :all])
  (:gen-class))

; In the long term, the rules engine needs to be rewritten.

(defrecord Happy [name])
(defrecord Loves [name1 name2])

(defrule rule1
  [Happy (= ?X name)]
  =>
  (println ?X "is happy!"))

(defrule rule2
  [Happy (= ?X2 name)]
  [Loves (= ?X1 name1) (= ?X2 name2)]
  =>
  (println ?X1 "makes" ?X2 "happy!"))

(defrule rule3
  [Loves (= ?X1 name1) (= ?X2 name2)]
  [Loves (= ?X2 name1) (= ?X1 name2)]
  =>
  (insert! (->Happy ?X1)))

(-> (mk-session 'genifer6.LoveTest)
    (insert (->Loves "John" "Mary")
            (->Loves "Mary" "John")
            (->Happy "Mary"))
    (fire-rules))
