grammar Math;

sum : NUMBER '+' NUMBER;

NUMBER : [0-9]+;
WS : [\t\n]+ -> skip;
