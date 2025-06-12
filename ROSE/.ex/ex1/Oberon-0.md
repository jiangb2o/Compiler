---
puppeteer:
    format: "A4"
    scale: 0.8
    margin:
        top: 1cm
        right: 1cm
        bottom: 1cm
        left: 1cm
print_background: true
---


[TOC]

### 编写正确的 Oberon-0 源程序以及变异程序  
我使用 Oberon-0 编写了一个求阶乘的程序. 该程序几乎包含 Oberon-0 的所有语法构造:  
* 模块: `MODULE FactorialProgram`
* 声明: 类型`TYPE BOOL = BOOLEAN`, 常量`CONST illegal = -1`, 变量`VAR result, i: INTEGER`  
* 过程声明`PROCEDURE Factorial(n: INTEGER)`, 过程调用`Factorial(n)`  
* 语句, 如赋值语句`legal := n >= 0`, `IF ELSE`语句, 和`WHILE`语句  
* 表达式如`i <= n`  

变异程序错误设计:  
* 词法错误 LexicalException  
    * factorial.001: 非法符号 IllegalSymbolException
    * factorial.002: 非法八进制数字格式 IllegalOctalException
    * factorial.003: 非法十进制数字格式 IllegalDecimalException
    * factorial.004: 非法数字常量长度 IllegalDigitLengthException
    * factorial.005: 非法标识符长度 IllegalIdentifierLengthException
* 语法错误 SyntacticException  
    * factorial006: 缺少分号 MissingSemicolonException
    * factorial007: 缺少END MissingENDException
    * factorial008: 缺少左括号 MissingLeftParenthesisException
    * factorial009: 缺少右括号 MissingRightParenthesisException
    * factorial010: 缺少操作符 MissingOperatorException
    * factorial011: 缺少操作数 MissingOperandException
* 语义错误 SemanticException  
    * factorial012: 函数参数不匹配 FunctionParameterMismatchedException
    * factorial013: 类型不匹配 TypeMismatchedException
    * factorial014: 除0运算 DividedByZeroException
    * factorial015: 未声明的标识符 UndeclaredIdentifierException

### Oberon-0 语言特点  

#### 保留字与关键字的区别  
* 保留字不能作为用户标识符使用, 在语法规则中有固定的结构意义, 编译器会阻止将保留字用作变量名, 过程名等.  
* 关键字具有特定的语义含义, 通常是类型或过程名, 但是用户可以使用关键字作为标识符来覆盖默认含义.  

#### 表达式语法规则不同之处  
1. 某些运算符符号不同, 如 `#` 和 `!=`, 逻辑运算符 `&`, `OR`, `~` 和 `&&`, `||`, `!`等  
2. Oberon-0类型更严格, 没有隐式类型转换, 也没有强制类型转换语法.  
3. Oberon-0的数字大小通过常量中数字个数来限制, 而java 和 C/C++是通过数字类型占字节数来进行限制.  
4. Oberon-0的表达式相对简单, 表达能力较弱, 缺少三目运算符, 位运算和自增自减等特性  

### Oberon-0 文法定义的二义性  
不存在二义性  

没有二义性的原因:  
* 运算符歧义: Oberon-0的BNF定义中, 表达式的定义采用了严格的分层语法, 严格定义了运算符的优先级.  
* if-else匹配歧义: Oberon-0通过END来明确结束对应语句作用范围.  
* 类型/变量冲突: C/C++中 (A)*B 可能表示类型转换, 也可能表示乘法. 但Oberon-0没有类型转换语法, 解决歧义.  

### 心得体会  
在本次实验中, 我了解了 Oberon-0 语言并通过与 java, C/C++ 语言的对比来加深对 Oberon-0 的理解. 另外, 我通过编写 Oberon-0 变异程序了解了如何筛选有效的测试数据. 
