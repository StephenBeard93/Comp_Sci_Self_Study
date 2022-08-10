.data
  myDouble:   .double 7.0002
  doubleZero: .double 0.0
  
.text
  ldc1 $f2, myDouble
  ldc1 $f0, doubleZero
  
  li $v0, 3
  add.d $f12, $f0, $f2
  syscall 
  
