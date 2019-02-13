(ns genifer6.GeneticAlgorithm)

;; Standard evolutionary algorithm:
;; ==============================
;; Initialize population
;; Repeat until success:
;;    Select parents
;;    Recombine, mutate
;;    Evaluate
;;    Select survivors
;; ==============================

;; * The genome consists of a set of rules which evolve co-operatively.
;; * Each candidate represents just one rule.
;; * Each rule has head, tail.
;; * Heads and tails are composed from "var" symbols and "const" symbols.

;; TO-DO:
;; * How to score rules?
;; * Rules have variable length, OK?
;;    -- as long as their lengths can decrease during learning

;; INPUT:
;; 1) examples
;; 2) goal / command
;; 3) background knowledge (BK) in the form of logic formulas

;; OUTPUT:
;; logic formulas + program that achieves the goal

;; POPULATION:
;; contains logic formulas and program steps (actions)

;; SCORING and EVALUATION:
;; 1) correct answers from examples = reward
;; 2) background knowledge may suggest "this is a good program"

;; We should beware especially of synergistic interactions

;; ** Initialize population
;; This is the set of current KB formulas, no need to initialize

;; Repeat until success:
;;    ** Select formulas to recombine
;;    ** Select formulas to mutate
;;    at this point we get some new candidates
;;    ** Evaluate new KB
;;        test KB on new / existing examples
;;    ** Select survivors
;;        based on scores

;; Constant parameters:
(def numBits 64)
(def maxGens 100)
(def popSize 100)
(def crossRate 0.98)
(def mutationRate (/ 1.0 numBits))

;; count number of 1's
(defn oneMax [bitString]
  (count (filter #{1} bitString)))

(defn fitness [gene]
    ;; println("fitness: " + gene)
    (oneMax gene))

(defn printCandidate [can]
  (print "〈" (fitness can) "〉 ")
  (doseq [bit can]
    (if (= bit 1)
      (print "▆")
      (print " ")))
  (println))

;; generate a string of length num_bits
(defn randomBitString []
  (take numBits (repeatedly #(rand-int 2))))
    ;; (print "new born: ")
    ;; (printCandidate s)

;; pick 2 random but *distinct* candidates, pick the one with higher fitness
(defn binaryTournament [pop]
  (let [i (rand-int numBits)
        j (rand-int numBits)
        x (nth pop i)
        y (nth pop j)]

  ;;while (i == j)
    ;;j = r.nextInt(pop.length)

  (if (> (fitness x) (fitness y))
    x
    y)))

;; Randomly mutate DNA.
;; DNA's length = # of times to attempt mutation.
;; rate = 1/(DNA's length), so longer strand, lower mutation rate
(defn pointMutate [gene & [mutation-rate]]
    (let [rate (if mutation-rate mutation-rate (/ 1.0 numBits))]

      ;; print("in:  "); printCandidate(gene)
      (map (fn [x] (if (> (rand) rate)
              x (- 1 x)))
        gene)))
      ;; print("out: "); printCandidate(result.toString())

;; Pick a point within Parent1,
;; cross Parent1's DNA with Parent2's
(defn crossover [parent1 parent2 crossover-rate]
    (if (> (rand) crossover-rate)
      parent1

      (let [i (rand-int (+ 1 numBits))]
        (concat (take i parent1) (drop i parent2)))))

    ;; println("p1: " + parent1)
    ;; println("mx: " + mix)
    ;; println("p2: " + parent2)
    ;; print  ("  : ")
    ;; for (i <- 0 until point)
    ;;  print(" ")
    ;; println("^\n")

;; Reproduce for 1 generation
;; Repeat N times:
;; - choose a candidate
;; - select the candidate next to him
;; - child = cross-over p1 with p2
;; - pointMutate child
(defn reproduce [selected popSize crossRate mutationRate]
    (map (fn [x1]
      (let [x2 (nth selected (rand-int popSize))
            child (crossover x1 x2 crossRate)]
          (pointMutate child mutationRate)))
      selected))

;; Compare fitness of 2 candidates
(defn cmpFitness [x1 x2]
    (compare (fitness x1) (fitness x2)))

;; Main algorithm for genetic search
;; - init population
;; - sort by fitness, reverse
;; - do maxGen times:
;;    - binaryTournament
;;    - reproduce
;;    - record best fitness, break if perfect
(defn evolve []
  ;; initialize population
  (def initPop (atom
          (reverse (sort cmpFitness
              (take popSize (repeatedly #(randomBitString)))))))

  (doseq [c @initPop]
    (printCandidate c))

  (def best (atom (first @initPop)))

  (loop [i maxGens]
    (println "Gen " i)
    (let [pop1 (reverse (sort cmpFitness
              (take popSize (repeatedly #(binaryTournament @initPop)))))
          pop2 (reverse (sort cmpFitness
              (reproduce pop1 popSize crossRate mutationRate)))]

      (if (>= (fitness (first pop2)) (fitness @best))
        (reset! best (first pop2)))

      (printCandidate @best)

      (reset! initPop pop2)

      (if (= (fitness @best) numBits)
        (println "Success!!!")
        (recur (- i 1))))))

;; Uncomment this to run:
;; (evolve)
