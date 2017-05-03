(FUNCTION  test  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 3)]  [(i 0)])
    (OPER 5 Mov [(r 1)]  [(r 3)])
    (OPER 6 Mov [(r 4)]  [(i 1)])
    (OPER 7 Mov [(r 2)]  [(r 4)])
    (OPER 8 Mov [(r 5)]  [(i 0)])
    (OPER 9 Mov [(r 2)]  [(r 5)])
    (OPER 10 Mov [(r 1)]  [(r 0)])
    (OPER 11 GT [(r 6)]  [(r 1)(r 2)])
    (OPER 12 BNE []  [(r 6)(i 0)(bb 6)])
  )
  (BB 4
    (OPER 13 Mov [(r 8)]  [(i 1)])
    (OPER 14 Sub_I [(r 7)]  [(r 2)(r 8)])
    (OPER 15 Mov [(r 2)]  [(r 7)])
  )
  (BB 6
  )
  (BB 7
    (OPER 16 Mov [(r 10)]  [(i 0)])
    (OPER 17 EQ [(r 9)]  [(r 1)(r 10)])
    (OPER 18 BEQ []  [(r 9)(i 0)(bb 8)])
    (OPER 19 Mov [(r 12)]  [(i 2)])
    (OPER 20 Sub_I [(r 11)]  [(r 1)(r 12)])
    (OPER 21 Mov [(r 1)]  [(r 11)])
  )
  (BB 8
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
