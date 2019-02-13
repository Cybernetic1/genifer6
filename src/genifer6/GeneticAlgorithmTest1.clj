(ns genifer6.GeneticAlgorithmTest1)

; This is the example from the book " Clever Algorithms", translated from Ruby

; Constant parameters:
(def numBits 64)
(def maxGens 100)
(def popSize 100)
(def crossRate 0.98)
(def mutationRate (/ 1.0 numBits))

; Count number of 1's
(defn oneMax [bitString]
  (count (filter #{1} bitString)))

(defn fitness [gene]
    (oneMax gene))

(defn printCandidate [can]
  (print "〈" (fitness can) "〉 ")
  (doseq [bit can]
    (if (= bit 1)
      (print "▆")
      (print " ")))
  (println))

; Generate a string of length num_bits
(defn randomBitString []
  (take numBits (repeatedly #(rand-int 2))))
    ; (print "new born: ")
    ; (printCandidate s)

; Pick 2 random (distinct?) candidates, select the one with higher fitness
; This step would be repeated for the entire population.
(defn binaryTournament [pop]
  (let [i (rand-int popSize)
        j (rand-int popSize)
        x (nth pop i)
        y (nth pop j)]

  ;while (i == j)
    ;j = r.nextInt(pop.length)

  (if (> (fitness x) (fitness y))
    x
    y)))

; Randomly mutate a gene.
; Gene's length = # of times to attempt mutation.
; rate = 1/(gene's length), so longer strand, lower mutation rate
(defn pointMutate [gene & [mutation-rate]]
  (let [rate (if mutation-rate
      mutation-rate
      (/ 1.0 numBits))]

    (map (fn [x] (if (> (rand) rate)
            x
            (- 1 x)))
        gene)))
 
; Pick a point within Parent1,
; cross Parent1's DNA with Parent2's
(defn crossover [parent1 parent2 crossover-rate]
  (if (> (rand) crossover-rate)
    parent1

    (let [i (rand-int (+ 1 numBits))]
      (concat (take i parent1) (drop i parent2)))))

  ; println("p1: " + parent1)
  ; println("mx: " + mix)
  ; println("p2: " + parent2)
  ; print  ("  : ")
  ; for (i <- 0 until point)
  ;  print(" ")
  ; println("^\n")

; Reproduce for 1 generation
; Repeat N times:
; - choose a candidate
; - select the candidate next to him
; - child = cross-over p1 with p2
; - pointMutate child
(defn reproduce [selected popSize crossRate mutationRate]
  (map (fn [x1]
    (let [x2 (nth selected (rand-int popSize))
          child (crossover x1 x2 crossRate)]
        (pointMutate child mutationRate)))
    selected))

; Compare fitness of 2 candidates
(defn cmpFitness [x1 x2]
  (compare (fitness x1) (fitness x2)))

; Main algorithm for genetic search
; - init population
; - sort by fitness, reverse
; - do maxGen times:
;    - binaryTournament
;    - reproduce
;    - record best fitness, break if perfect
(defn evolve []
  ; initialize population
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

; Uncomment this to run:
; (evolve)
