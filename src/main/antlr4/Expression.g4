// https://alexecollins.com/antlr4-and-maven-tutorial/
grammar Expression;

exp : '('NUMBER CRITERION NUMBER')' | '('EXPRESSION CRITERION NUMBER')';

CRITERION : '=' | '&&' | '||';

NUMBER : [0-9]+;

WS : [\t\n]+ -> skip; // do not consider tabs and new lines as a token