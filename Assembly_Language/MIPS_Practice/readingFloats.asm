.data
  message: .asciiz "Enter the value of PI: "
  zeroFloat: .float 0.0
.text
  # load zero to register 
  lwc1 $f4, zeroFloat
  
  # load and display message
  li $v0, 4
  la $a0, message
  syscall 
  
  # Get user input
  li, $v0, 6 
  syscall 
  
  # Display user input 
  li, $v0, 2
  add.s $f12, $f4, $f0
  syscall 
  
  
