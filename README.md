# KnightsTour
An implementation of the Knight's Tour problem using both brute force and heuristic methods

-h Flag enables hueristic analysis, otherwise program will default to brute force. To run, enter the size of the board as an int and the 
x and y coordinates of the starting position. For instance, a board with a heigh and width of 6 starting at position (0,0) would be entered as:

    KnightsTour 6 0 0
    
Whereas the same board checked using Warnsdorff's Algorithm would be input as
    
    KnightsTour -h 6 0 0

Dependiencies:
* Apache Commons CLI 1.4 library 

Sources:
* https://github.com/mariocervera/CodingChallenges/blob/master/CombinatorialSearch/KnightTour.java
