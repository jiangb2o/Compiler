import java.io.*;
import exceptions.*;

%%

%public
%class OberonScanner
%ignorecase
%unicode
%line
%column
%yylexthrow LexicalException
%type String
%eofval{
	return "EOF";
%eofval}
%{
    int getLine() { return yyline; }
    int getColumn() { return yycolumn; }
%}

Reserved = "MODULE" | "BEGIN" | "END" | "CONST" | "TYPE" | "VAR" | "PROCEDURE" | "RECORD" | "ARRAY" | "OF" | "WHILE" | "DO" | "IF" | "THEN" | "ELSIF" | "ELSE" | "DIV" | "MOD" | "OR"

Keyword = "INTEGER" | "BOOLEAN" | "READ" | "WRITE" | "WRITELN"

Operator = "=" | ":=" | "(" | ")" | "#" | "<" | "<=" | ">" | ">=" | "+" | "-" | "*" | "DIV" | "MOD" | "&" | "OR" | "~" | "[" | "]"

Punctuation = ":" | ";" | "," | "."

Comment = "(*" ~ "*)"

Integer = [1-9][0-9]* | 0[0-7]*

Identifier = [a-zA-Z][a-zA-Z0-9]*

WhiteSpace = " " | \r | \n | \r\n | \t

IllegalOctal = 0[0-7]* [8 | 9 | "."]+ [0-9]*

IllegalDecimal = {Integer}+ ["." | {a-zA-Z}]+ [0-9]*

MismatchedComment = "(*" ( [^*] | \*+ [^*)] )* [\*]* | \$

%%

<YYINITIAL>
{
{Reserved}          {return "Reserved";}
{Keyword}           {return "Keyword";}
{Operator}          {return "Operator";}
{Punctuation}       {return "Punctuation";}
{Comment}           {return "Comment";}
{Integer}           {
                        if(yylength() > 12)
                            throw new IllegalDigitLengthException();
                        else
                            return "Integer";
                    }
{Identifier}        {
                        if(yylength() > 24)
                            throw new IllegalIdentifierLengthException();
                        else
                            return "Identifier";
                    }
{WhiteSpace}        {}
{IllegalOctal}      {throw new IllegalOctalException();}
{IllegalDecimal}    {throw new IllegalDecimalException();}
{MismatchedComment} {throw new MismatchedCommentException();}
.                   {throw new IllegalSymbolException();}
}