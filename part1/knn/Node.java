package knn;
/**
 * %    -- The attributes are (dontated by Riccardo Leardi,
% 	riclea@anchem.unige.it )
%  	1) Alcohol
%  	2) Malic acid
%  	3) Ash
% 	4) Alcalinity of ash
%  	5) Magnesium
% 	6) Total phenols
%  	7) Flavanoids
%  	8) Nonflavanoid phenols
%  	9) Proanthocyanins
% 	10)Color intensity
%  	11)Hue
%  	12)OD280/OD315 of diluted wines
%  	13)Proline
 */
public class Node {
public double[] attributes;
public void setAttributes(double[] a){
    this.attributes=a;
}
public int classification;
public void setClassification(int a){this.classification=a;}
}