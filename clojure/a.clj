(ns sum.core
  (:require [clojure.core.reducers :as r]))

(defn mysum [numbers]
  (reduce + numbers))

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
  (if (== 0 (mod n 2))
    (+ n 7)
    (* n 2))
)

(defn my-seq 
  ([] (my-seq 1))
  ([x] (lazy-seq (cons x (my-seq (zz x)))))
)

(take 10 (my-seq))

(defn positive-numbers 
	([] (positive-numbers 1))
	([n] (lazy-seq (cons n (positive-numbers (inc n))))))