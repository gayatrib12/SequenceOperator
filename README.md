# SequenceOperator
Perform Sequence generation, partitioning, assembling

CAP 5510 - Bioinformatics

Group members - 

1. Prudhvee Narasimha Sadha(4689-9656)
2. Gayatri Behera (3258-9909)


This project accomplishes -
1. Sequence Generation
2. Sequence Partitioning
3. Sequence Assembling

Commands to run the code -
1. To compile the java files - javac *.java
2. To run the SequenceGenerator - java Hw1_1 1000 25 25 25 25 10 0.1 "first_output.txt"
3. To run the SequencePartitioner - java Hw1_2 "first_output.txt" 100 150 "second_output.txt"
4. To run the SequenceAssebler - java  Hw1_3 second_output.txt 1 -1 -3 third_output.txt