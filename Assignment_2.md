# CS3383 - Assignment 2

**Author:** Shahriar Kariman

*Due:* 2024-08-10

## Question 1 - Student Taking a Test

It is unclear what the goal is for part a is it that same as part b? or is the students goal to get the most marks in which case? does that mean the longer question have more marks directly proportional to the amount of time the student has to spend on it?

### A - Greedy Algorithm to Pick Questions

Lets assume:

$$
\begin{split}
  T = [1, 3, 5, 6, 6]
  \\
  K = 14
\end{split}
$$

In this case obviously the best outcome is when the student picks questions 2, 3 and 4 regardless of weather or not he is optimizing his grade or the number of questions solved. But the greedy algorithm will do only questions 4 and 5 before running out of time so clearly the greedy algorithm does not always generate the optimal solution.

### B - Proof that The Greedy Alg. is Optimal

Here is my greedy algorithm:

```py
def testStrategy(K, T):
  while True:
    i = findShortest(T)
    if T[i] <= K:
      # complete the question
      K = K - T[i]
      T[i] = -T[i]  # marking the question as done
    else:
      break
```

Lets assume there is a better solution that solves more questions than the greedy one. In this case the new solution will have one more solved question than the greedy algorithm. We know if $T_g$ is the list of $l$ questions chosen using the greedy algorithm then:

$$
\begin{split}
  \sum_{i=1}^{l} T_g = t_g
  \\
  i^\prime = findShortest(T-T_g)
  \\
  then\ K > t_g + T[i^\prime]
\end{split}
$$

Given that this proves that even the shortest job left over can't be completed in addition to the questions chosen by the greedy algorithm so if the new algorithm is optimal then it must not have at least one of the questions in the $T_g$ set to make room for 2 or more questions but if we swap a question $q \in T_g$ with a question $p \in (T-T_g)$ to get a new set $T_s$ then we are guaranteed to have $T[p] > T[q]$ because at each turn in the greedy algorithm we chose the shortest question so even if $\sum_{i=1}^{l} T_s < K$ no more questions can be fit into the new set which means the new algorithm can't pick more questions than the greedy algorithm which is **contradictory**.

## Question 2 - Books & Movable Shelves

She could have just bought a Kindle and saved us all the trouble.

### A - Greedy Algorithm to Shelves Books

So if I want to put the tallest books take up the least number of shelves and I can do that by only placing the tallest books first then the second tallest, etc. this works because if I put a shorter book in between 10 tall books then an extra tall book would need a new shelf and that increases the shelf size of the next shelf.

```py
def booksOnShelf(B):
  # Input B: an array of book objects containing their height
  sorted_B = mergeSort(B) # sort books by height (decending)
  shelfs = [] # a 2D array where each element is a shelf
  shelf = [] # a shelf is array of size 10 containing books
  for i in range(len(sorted_B)):
    shelf.add(sorted_B[i])
    if(len(shelf)==10):
      shelfs.add(shelf)
      shelf = []
```

### B - Run Time Analysis

So the first step of the algorithm is sorting the array which is $\Theta(n \log{n})$. The content of the for loop is $\Theta(n)$ since we are iterating over the array of books only once.

Which makes the run time analysis of this algorithm $\Theta(n \log{n})$.

### C - Proof

Lets assume that the  greedy algorithm is not optimal meaning there is an input case where it does not generate the optimal solution that means there is at least 2 elements $i$ and $j$ in the sorted array of books where $height(b_i) < height(b_j)$ and $i < j$ such that if I swap $i$ and $j$ the total shelf height will decrease if $i$ and $j$ aren't on the same shelf.

If I call the height of the shelf in the greedy algorithm solution where $i$ resides $h_i$ and where $j$ resides $h_j$

We know:

$$
  since\ i<j \rightarrow h_i<h_j
$$

then after swapping $i$ and $j$ the height of the new shelves would be $h_i^\prime$ and $h_j^\prime$ assuming the tallest book in row where j resided is k after the swap:

$$
\begin{split}
  since\ height(b_i) < height(b_j) \rightarrow
  \begin{cases}
    h_i + a = h_i^\prime,& a = len(b_j) - h_i > 0
    \\
    h_j + b = h_j^\prime,& b = len(b_k) - len(b_j)
  \end{cases}
\end{split}
$$

that means that the new total height is:

$$
  h_t^\prime = h_t-h_i-h_j+h_i^\prime+h_j^\prime = h_t^\prime + a + b = h_t^\prime + len(b_j) + len(b_k) - len(b_j) =  h_t^\prime + len(b_k)
$$

and since we know $a$ and $b$ are positive the height will always increase with a swap which is **contradictory** because we should have had a case where a swap reduces the total height and that means the greedy algorithm is always optimal.

## Question 3 - Minimizing Average Retrieval Time

Why are we still using tape?

### A - Greedy Algorithm to Find Minimum Average Retrieval Time

$$
\begin{split}
  time\ to\ retrieve\ file\ k \rightarrow T_k = \sum_{j=1}^k {L_{i_j}}
  \\
  average\ retrieval\ time\ \rightarrow A = \frac{1}{n} \times \sum_{k=1}^{n} T_k = \frac{1}{n} \times \sum_{k=1}^{n} \sum_{j=1}^k {L_{i_j}}
\end{split}
$$

So if I put the largest file first it will increase the retrieval time for all of the files after it so I will put the smallest files first.

```py
def filesOnTape(files):
  # input is an array of files we would like to put on tape
  sorted_files = mergeSort(files) # sort files by length (ascending)
  l = 0
  total_retrival_time = 0
  for i in range(len(sorted_files)):
    write_to_tape(sorted_files[i])
    l = l + sorted_files[i].file_length
    total_retrival_time = total_retrival_time + l
  return total_retrival_time / len(sorted_files)
```

### B - Run Time Analysis

So the first step is as sorting the files which is $\Theta(n \log{n})$ and we need to loop through the sorted array to write the files in the taps and to calculate the average retrieval time which is $\Theta(n)$ and that make the running time of the algorithm $\Theta(n \log{n})$.

### C - Proof that the Greedy Algorithm is Optimal

Lets assume that the greedy algorithm $G$ is not optimal and another algorithm $S$ is better than $G$. Then $S$ must not be sorting the files by length and that tells me there are at least 2 files $p$ and $q$ that are next to each such that:

$$
\begin{split}
  i_q = i_p + 1 \rightarrow q\ comes\ after\ p
  \\
  L_q < L_p \rightarrow p\ is\ longer\ than\ q
\end{split}
$$

Now if there was another algorithm $S^\prime$ exactly the same as $S$ but with p and q swapped. The new retrieval time for $p$ and $q$ would be:

$$
\begin{split}
 T_p^\prime = T_q - L_q + L_p = (T_p - L_p) - L_q + L_p = T_p - L_q
 \\
 T_q^\prime = T_p^\prime + L_q = T_q + L_p
\end{split}
$$

Now for all of the elements at $i$ after $i_p^\prime$ in $S^\prime$.

$$
\begin{split}
  T_k^\prime = T_p^\prime + \sum_{j=i_p^\prime}^k {L_{i_j}}
  \\
  T_k^\prime = T_p - L_q + \sum_{j=i_p^\prime}^k {L_{i_j}}
\end{split}
$$

The retrieval time for elements before $i_p$ hasn't changed but for every element after $i_q$ the retrieval time will increase by $T_p - L_q$ amount and since we know that $T_p \geq L_p$ then for all elements after $i_q$ there has been an increase in retrieval time. Even if $p$ and $q$ are at the last elements the combined retrieval time would have increase by $L_p - L_q$ which we know if greater than 0.

Therefore $S^\prime$ is more optimal than $S$ and if we find another elements similar to $p$ and $q$, propose another algorithm $S^{\prime \prime}$ we will find that $S^{\prime \prime}$ is more optimal that $S^\prime$ repeating this process will bring us back to a sorted list of file the same as the one $G$ uses which makes the greedy algorithm more optimal than $S$ and all algorithms which is **contradictory** since $S$ is meant to be more optimal than $G$.

And that means that the greedy algorithm is optimal.

## Question 4 - Solving Recurrence Equation

Yay recurrence equations. $\rightarrow$ (sarcasm)

### Part A

Let me guess the answer is $O(n \log{n})$

$$
  T(n) = O(n) \rightarrow T(n) \leq (c \times n \log{n}),\ \exists c > 0, \forall n \geq n_0
$$

So now I need to prove:

$$
  T(n) \leq (n \log{n})\ assuming\ T(m) \leq c \times m \log{m}, \forall m < n
$$

Based on what we just assumed we know:

$$
  for\ m = \frac{n}{4} < n \rightarrow T(\frac{n}{4}) \leq c \times \frac{n}{4} \times \log{\frac{n}{4}}
$$

Now if I put that inside the recurrence equation:

$$
\begin{split}
  T(n) = 4 \times T(\frac{n}{4}) + 1 \leq 4 \times c \times \frac{n}{4} \times \log{\frac{n}{4}}
  \\
  T(n) \leq c \times n \log{\frac{n}{4}}
  \\
  T(n) \leq c \times n (\log{n}-\log{4}) \leq c \times n \log{n}
  \\
  \boxed{T(n) \leq c \times n \log{n}}
\end{split}
$$

So this proves our assumption is correct and $T(n) = \Theta(n \log{n})$.

### Part B

$$
\begin{split}
  T(n) = 16 \times T(n/4) + n \rightarrow
    \begin{cases}
      a = 16
      \\
      b = 4
      \\
      f(n) = n
    \end{cases}
    \\
    f(n) = O(n) = O(n^{\log_{b}{a}-\epsilon})\ for\ \epsilon = 1
\end{split}
$$

Clearly this is case 1 of the master theorem in which case $T(n) = \Theta(n^{\log_{b}{a}}) = \Theta(n^2)$

### Part C

$$
\begin{split}
  T(n) = 4 \times T(n/2) + n^2 \rightarrow
    \begin{cases}
      a = 4
      \\
      b = 2
      \\
      f(n) = n^2
    \end{cases}
    \\
    n^{\log_{b}{a}} = n^2 \rightarrow f(n) = \Theta(n^{\log_{b}{a}} \log^{k} {n})\ for\ k = 0
\end{split}
$$

According to the $2_{nd}$ case of the master theorem $T(n)=\Theta(n^2 \log{n})$.

### Part D

$$
\begin{split}
  T(n) = 9 \times T(n/3) + n^2 \log{n} \rightarrow
    \begin{cases}
      a = 9
      \\
      b = 3
      \\
      f(n) = n^2 \log{n}
    \end{cases}
    \\
    \log_{b}{a} = 2 \rightarrow f(n) = \Theta(n^{\log_{b}{a}} \log^{k}{n})\ for\ k = 1
\end{split}
$$

Which means according to case 2 of the master theorem $T(n) = \Theta(n^2 \log^{2}{n})$.

### Part E

$$
\begin{split}
  T(n) = 6 \times T(n/3) + n^2 \log{n} \rightarrow
    \begin{cases}
      a = 6
      \\
      b = 3
      \\
      f(n) = n^2 \log{n}
    \end{cases}
    \\
    \log_{b}{a} = \log_{3}{6} < 2 \rightarrow f(n) = \Omega(n^{\log_{b}{a} + \epsilon})\ for\ \epsilon = 1
    \\
    \&
    \\
    a \times f(\frac{n}{b}) = 6 \times \frac{n^2}{9} \times \log{\frac{n}{3}} = \frac{2}{3}n^2(\log{n}-\log{3}) \leq \delta f(n), \exists \delta < 1
\end{split}
$$

according to the $3_{rd}$ case of the master theorem $T(n) = \Theta(f(n)) = \Theta(n^2 \log{n})$.

## Question 5 - Analyzing a Divide and Conquer Algorithm

I usually use markup languages for my assignments which makes them look neat but in this case the drawing language I use(mermaid) can't make a readable recursion tree because the branching factor is so high. Even as I am drawing the tree by hand I am struggling to make it look presentable[^1] this is torture I don't see why the assignment couldn't include a simpler example for recursion tree[^2].

[^1]: I don't envy the TA reading this paragraph they probably had to look at 50 other incoherent diagrams today.

[^2]: If I wanted to spend 40 minutes drawing a tree I would have gotten an art degree.

### A - Asymptotic Analysis

At the end I was so unsatisfied with my artistic capabilities that I decided not to include the diagram and use a table instead.

| Depth | T's      | Size            | Time                 |
|-------|----------|-----------------|----------------------|
| 0     | 1        | $n$             | $n$                  |
| 1     | 4        | $\frac{n}{3}$   | $4\frac{n}{3}$       |
| 2     | $4^2$    | $\frac{n}{3^2}$ | $4^2\frac{n}{3^2}$   |
| i     | $4^i$    | $\frac{n}{3^i}$ | $4^i\frac{n}{3^i}$   |

As you can see the total cost at $i_{th}$ depth level is $4^i\frac{n}{3^i}$ which is $\Theta(n)$ and the total number of levels is equal to the number of time we can divide by 3 which is $\log_{3}{n}$ which makes $T(n) = \Theta(n \log{n})$.

### B - Change in Algorithm

Same story really.

| Depth | T's      | Size            | Time                     |
|-------|----------|-----------------|--------------------------|
| 0     | 1        | $n$             | $n^2$                    |
| 1     | 4        | $\frac{n}{3}$   | $4(\frac{n}{3})^2$       |
| 2     | $4^2$    | $\frac{n}{3^2}$ | $4^2(\frac{n}{3^2})^2$   |
| i     | $4^i$    | $\frac{n}{3^i}$ | $4^i(\frac{n}{3^i})^2$   |

Here the total cost at $i_{th}$ depth level is $(\frac{4}{9})^i \times n^2$ which is $\Theta(n^2)$ and the total number of levels is the same as before which means:

$$
  T(n) = n^2 \times \biggl( 1 + \frac{4}{9} + (\frac{4}{9})^2 + \dots \biggl)
$$

and since each number in that sequence becomes smaller I could say it is like:

$$
  T(n) = n^2 \times c,\ 1 < c < 2
$$

which makes $T(n) = \Theta(n^2)$.
