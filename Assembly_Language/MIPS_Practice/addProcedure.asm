.data 

.text
   main:
     addi $a0, $zero, 100
     addi $a1, $zero, 50
     jal addNumbers
     
     li $v0, 1
     add $a0, $zero, $v1
     syscall
     
     li $v0, 10
     syscall
   
   addNumbers:
     add $v1, $a0, $a1
     jr $ra   