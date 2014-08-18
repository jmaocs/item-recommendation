item_recommendation
====================

This includes three files

* data

		- like_items.csv: the original dataset
		- samples.arff: sampled and converted data from the whole dataset like_items.csv 
* recom

		- recommen-likes(python): given likes, recommend likes 
		- Transform.java: get a sample of original dataset like_items.csv and convert it to samples.arff which can be recognized by weka
		- Restore.java: restore the association rules from apriori_results.txt
* weka_results

		- all_items.txt: items for all likes 
		- apriori_result.txt: the generated results (all frequent itemsets and association rules) from weka apriori
		- association_rules.txt: all the association rules generated from weka apriori
* data_clean
		- PairCount.java: mapreduce algorithm to count coocurrances of pairs in the whole dataset and cutoff those infrequent pairs according to a min_support threshold
		- TextPair.java: User-defined type of pair of likes
		- HighFreqItems.java: mapreduce algorithm to count frequency of all likes in the whole dataset and cutoff thoes infrequent likes according to a min_support threshold
   
Dependency

* The required dependency is Numpy

To run the program

* python assign_tag.py
* python calc_sim.py > recommended_videos.txt