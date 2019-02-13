(ns genifer6.core
  (:require [clara.rules :refer :all])
  (:require [genifer6.GeneticAlgorithmTest1])
  (:require [genifer6.GeneticAlgorithm :refer [evolve]])
  (:require [genifer6.LoveTest])
  (:gen-class))

(defn -main
  [& args]
  (println "Welcome to Genifer 6"))
