.data
  message: .asciiz "Enter the value of e: "
  zeroFloat: .float 0.0
.text
  # load zero to register 
  lwc1 $f4, zeroFloat
  
  # load and display message
  li $v0, 4
  la $a0, message
  syscall 
  
  # Get user input
  li, $v0, 7
  syscall 
  
  # Display user input 
  li, $v0, 3
  add.d $f12, $f4, $f0
  syscall 