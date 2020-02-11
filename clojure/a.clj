(ns sum.core
  (:require [clojure.core.reducers :as r]))

(defn mysum [numbers]
  (reduce + numbers))

;; fold = parallel reduce
(defn my-parallel-sum [numbers]
  (r/fold + numbers))

(defn reduce-sum [numbers]
  (reduce (fn [acc x] (+ acc x)) 0 numbers))
;; equivalent to
(defn reduce-sum [numbers]
  (reduce #(+ %1 %2) 0 numbers))


(def numbers (into [] (range 0 10000000)))

(time (mysum (range 0 10000000)))
(time (mysum (range 0 10000000)))

(time (my-parallel-sum (range 0 10000000)))
(time (my-parallel-sum (range 0 10000000)))


(defn word-frequencies [words]
  (reduce 
    (fn [counts word] (assoc counts word (inc (get counts words))))
    {} words))

;; split str
(def get-words [text] (re-seq #"\w+" text))

(defn count-words-sequential [pages]
  (word-frequencies (mapcat get-words pages)))


;; lazy-seq
(defn fib 
  ([] (fib 1 1))
  ([a b] 
    (lazy-seq (cons a (fib b (+ a b))))
  )
)
(take 10 (fib))


(defn zz [n]
  (if (even? n)
    (+ n 7)
    (* n 2))
)

(defn my-seq 
  ([] (my-seq 1))
  ([x] (lazy-seq (cons x (my-seq (zz x)))))
)

(take 10 (my-seq))



;; pmap == parallel map
(pmap #(word-frequencies (get-words %)) pages)

;; ex of merge-with
(def merge-counts (partial merge-with +))
(merge-counts {:a 1 :b 0 :c 5} {:b 1} {:d 3})

(defn count-words-parallel [pages]
  (reduce (partial merge-with +)
    (pmap #(word-frequencies (get-words %)) pages)))


;; (defprotocol CollReduce
;;   (coll-reduce [coll f] [coll f init]))


(defn my-reduce
  ([f coll] (coll-reduce coll f))
  ([f init coll] (coll-reduce coll f init))
)

;; parallel calls to no arg functions
(pcalls (partial (partial + 2) 3))

;; parallel evaluations of expressions
(pvalues (rand-int 10) (+ 1 2) (rand-int 5))

(take 10 (repeatedly #(rand-int 10)))



