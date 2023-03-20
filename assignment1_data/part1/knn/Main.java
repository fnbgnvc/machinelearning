package knn;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
/*
 * k-nearest neighbour classification.
 * take training set wine-training, 
 * normalize scales of variables (min-max normalization)
 * for each item in wine-test, predict classes according
 * to euclidean distance- nearest neighbour
 * 
 * Your program should classify each instance in the test set wine-test according
to the training set wine-training. Note that the final column in these files list the class label for
each instance. Your program should take two file names as command line arguments, and classify
each instance in the test set (the second file name) according to the training set (the first file name).
 * 
 */

//first: read files

/**
 * euclidian distance calc: d(a,b)= sqrt(sum((ai-bi)^2/Rangei^2))
 */