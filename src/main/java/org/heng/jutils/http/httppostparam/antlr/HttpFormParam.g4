/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
grammar HttpFormParam;

  

r: (Pair) ( '&' Pair )*;


Pair: WS?  PName WS?  '='  PVal? ;


PName: [-.a-zA-Z_][-.a-zA-Z0-9_]*;//

PVal: ~[=&]+;//



WS : [ \t\r\n]+ -> skip; 

 
   