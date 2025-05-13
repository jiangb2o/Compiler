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

## 设计文档  

### 二义性分析
<img src="img/BNF.png"> </img>  
要证明此语法定义存在二义性, 只需要找到一个句子能由两个不同的语法树构建  
如1+2+3  
<img src="img/1.png"> </img>  

### 词法分析的设计与实现
有限自动机(状态转换图)如下:  
<img src="img/DFA.jpg"> </img>  
其中[0-9]表示0-9中任意一个数字, 转换箭头上字符多于一个时表示任意一个字符都能进行转换.  


### 算符优先关系定义

### 语法分析与语义处理的设计与实现

### 程序运行的屏幕截图

### 实验的心得体会

