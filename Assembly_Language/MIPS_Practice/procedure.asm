.data
  message: .asciiz "My name is lord \n LORD!"

.text
  main:
    jal thatsNotMyName
    
    li $v0, 10
    syscall 
  
  
  thatsNotMyName:
    li $v0, 4
    la $a0, message
    syscall
    
    jr $ra
    