### Percolation assignment

**87/100 result points**

Implementation without using WeightedQuickUnionUF library, so some timing tests targetting on count library methods calls fails.

Use one flat array for storing open/closed sites(cells) and cluster tree without init cycle loop.

Failed test: some problems with **isFull** method - not quite clear specification for its behavior.
