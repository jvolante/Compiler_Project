(DATA  a)
(FUNCTION  addThem  [(int d) (int e)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Add_I [(r 5)]  [(r 1)(r 3)])
    (OPER 5 Mov [(r 4)]  [(r 5)])
    (OPER 6 Mov [(m RetReg)]  [(r 4)])
    (OPER 7 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  putDigit  [(int s)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 3)]  [(i 48)])
    (OPER 5 Add_I [(r 2)]  [(r 3)(r 1)])
    (OPER 6 Pass []  [(r 2)])
    (OPER 7 JSR []  [(s putchar)])
    (OPER 8 Mov [(r 4)]  [(m RetReg)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
)
(FUNCTION  printInt  [(int r)]
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 4)]  [(i 0)])
    (OPER 5 Mov [(r 3)]  [(r 4)])
    (OPER 6 Mov [(r 6)]  [(i 10000)])
    (OPER 7 GTE [(r 5)]  [(r 1)(r 6)])
    (OPER 8 BEQ []  [(r 5)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 9 Mov [(r 7)]  [(i 45)])
    (OPER 10 Pass []  [(r 7)])
    (OPER 11 JSR []  [(s putchar)])
    (OPER 12 Mov [(r 8)]  [(m RetReg)])
    (OPER 13 Mov [(r 9)]  [(i 1)])
    (OPER 14 Pass []  [(r 9)])
    (OPER 15 JSR []  [(s putDigit)])
    (OPER 16 Mov [(r 10)]  [(m RetReg)])
    (OPER 17 Jmp []  [(bb 1)])
  )
  (BB 6
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 11
    (OPER 48 Mov [(r 30)]  [(i 1)])
    (OPER 49 EQ [(r 29)]  [(r 3)(r 30)])
    (OPER 50 BEQ []  [(r 29)(i 0)(bb 15)])
  )
  (BB 13
    (OPER 51 Mov [(r 31)]  [(i 0)])
    (OPER 52 Pass []  [(r 31)])
    (OPER 53 JSR []  [(s putDigit)])
    (OPER 54 Mov [(r 32)]  [(m RetReg)])
  )
  (BB 15
    (OPER 55 Jmp []  [(bb 12)])
  )
  (BB 17
    (OPER 69 Mov [(r 42)]  [(i 1)])
    (OPER 70 EQ [(r 41)]  [(r 3)(r 42)])
    (OPER 71 BEQ []  [(r 41)(i 0)(bb 21)])
  )
  (BB 19
    (OPER 72 Mov [(r 43)]  [(i 0)])
    (OPER 73 Pass []  [(r 43)])
    (OPER 74 JSR []  [(s putDigit)])
    (OPER 75 Mov [(r 44)]  [(m RetReg)])
  )
  (BB 21
    (OPER 76 Jmp []  [(bb 18)])
  )
  (BB 5
    (OPER 18 Mov [(r 12)]  [(i 1000)])
    (OPER 19 GTE [(r 11)]  [(r 1)(r 12)])
    (OPER 20 BEQ []  [(r 11)(i 0)(bb 9)])
  )
  (BB 7
    (OPER 21 Mov [(r 14)]  [(i 1000)])
    (OPER 22 Div_I [(r 13)]  [(r 1)(r 14)])
    (OPER 23 Mov [(r 2)]  [(r 13)])
    (OPER 24 Pass []  [(r 2)])
    (OPER 25 JSR []  [(s putDigit)])
    (OPER 26 Mov [(r 15)]  [(m RetReg)])
    (OPER 27 Mov [(r 18)]  [(i 1000)])
    (OPER 28 Mul_I [(r 17)]  [(r 2)(r 18)])
    (OPER 29 Sub_I [(r 16)]  [(r 1)(r 17)])
    (OPER 30 Mov [(r 1)]  [(r 16)])
    (OPER 31 Mov [(r 19)]  [(i 1)])
    (OPER 32 Mov [(r 3)]  [(r 19)])
  )
  (BB 9
    (OPER 33 Mov [(r 21)]  [(i 100)])
    (OPER 34 GTE [(r 20)]  [(r 1)(r 21)])
    (OPER 35 BEQ []  [(r 20)(i 0)(bb 11)])
  )
  (BB 10
    (OPER 36 Mov [(r 23)]  [(i 100)])
    (OPER 37 Div_I [(r 22)]  [(r 1)(r 23)])
    (OPER 38 Mov [(r 2)]  [(r 22)])
    (OPER 39 Pass []  [(r 2)])
    (OPER 40 JSR []  [(s putDigit)])
    (OPER 41 Mov [(r 24)]  [(m RetReg)])
    (OPER 42 Mov [(r 27)]  [(i 100)])
    (OPER 43 Mul_I [(r 26)]  [(r 2)(r 27)])
    (OPER 44 Sub_I [(r 25)]  [(r 1)(r 26)])
    (OPER 45 Mov [(r 1)]  [(r 25)])
    (OPER 46 Mov [(r 28)]  [(i 1)])
    (OPER 47 Mov [(r 3)]  [(r 28)])
  )
  (BB 12
    (OPER 56 Mov [(r 34)]  [(i 10)])
    (OPER 57 GTE [(r 33)]  [(r 1)(r 34)])
    (OPER 58 BEQ []  [(r 33)(i 0)(bb 17)])
  )
  (BB 16
    (OPER 59 Mov [(r 36)]  [(i 10)])
    (OPER 60 Div_I [(r 35)]  [(r 1)(r 36)])
    (OPER 61 Mov [(r 2)]  [(r 35)])
    (OPER 62 Pass []  [(r 2)])
    (OPER 63 JSR []  [(s putDigit)])
    (OPER 64 Mov [(r 37)]  [(m RetReg)])
    (OPER 65 Mov [(r 40)]  [(i 10)])
    (OPER 66 Mul_I [(r 39)]  [(r 2)(r 40)])
    (OPER 67 Sub_I [(r 38)]  [(r 1)(r 39)])
    (OPER 68 Mov [(r 1)]  [(r 38)])
  )
  (BB 18
    (OPER 77 Pass []  [(r 1)])
    (OPER 78 JSR []  [(s putDigit)])
    (OPER 79 Mov [(r 45)]  [(m RetReg)])
    (OPER 80 Jmp []  [(bb 6)])
  )
)
(FUNCTION  main  []
  (BB 2
    (OPER 3 Func_Entry []  [])
  )
  (BB 3
    (OPER 4 Mov [(r 6)]  [(i 5)])
    (OPER 5 Mov [(r 2)]  [(r 6)])
    (OPER 6 Mov [(r 1)]  [(r 2)])
    (OPER 7 Mov [(r 8)]  [(i 5)])
    (OPER 8 EQ [(r 7)]  [(r 1)(r 8)])
    (OPER 9 BEQ []  [(r 7)(i 0)(bb 5)])
  )
  (BB 4
    (OPER 10 Mov [(r 9)]  [(i 3)])
    (OPER 11 Store []  [(r 9)(s a)])
  )
  (BB 6
    (OPER 15 Mov [(r 11)]  [(i 0)])
    (OPER 16 Mov [(r 3)]  [(r 11)])
    (OPER 17 Mov [(r 12)]  [(i 1)])
    (OPER 18 Mov [(r 5)]  [(r 12)])
    (OPER 19 Mov [(r 14)]  [(i 8)])
    (OPER 20 LTE [(r 13)]  [(r 5)(r 14)])
    (OPER 21 BEQ []  [(r 13)(i 0)(bb 8)])
  )
  (BB 7
    (OPER 22 Add_I [(r 15)]  [(r 3)(r 5)])
    (OPER 23 Mov [(r 3)]  [(r 15)])
    (OPER 24 Mov [(r 17)]  [(i 1)])
    (OPER 25 Add_I [(r 16)]  [(r 5)(r 17)])
    (OPER 26 Mov [(r 5)]  [(r 16)])
    (OPER 27 Mov [(r 19)]  [(i 8)])
    (OPER 28 LTE [(r 18)]  [(r 5)(r 19)])
    (OPER 29 BNE []  [(r 18)(i 0)(bb 7)])
  )
  (BB 8
    (OPER 30 Mov [(r 21)]  [(i 3)])
    (OPER 31 Div_I [(r 20)]  [(r 3)(r 21)])
    (OPER 32 Mov [(r 4)]  [(r 20)])
    (OPER 33 Mov [(r 23)]  [(i 4)])
    (OPER 34 Mul_I [(r 22)]  [(r 4)(r 23)])
    (OPER 35 Mov [(r 3)]  [(r 22)])
    (OPER 36 Mov [(r 25)]  [(i 48)])
    (OPER 37 Load [(r 26)]  [(s a)])
    (OPER 38 Add_I [(r 24)]  [(r 25)(r 26)])
    (OPER 39 Pass []  [(r 24)])
    (OPER 40 JSR []  [(s putchar)])
    (OPER 41 Mov [(r 27)]  [(m RetReg)])
    (OPER 42 Mov [(r 29)]  [(i 48)])
    (OPER 43 Add_I [(r 28)]  [(r 29)(r 1)])
    (OPER 44 Pass []  [(r 28)])
    (OPER 45 JSR []  [(s putchar)])
    (OPER 46 Mov [(r 30)]  [(m RetReg)])
    (OPER 47 Load [(r 31)]  [(s a)])
    (OPER 48 Pass []  [(r 1)])
    (OPER 49 Pass []  [(r 31)])
    (OPER 50 JSR []  [(s addThem)])
    (OPER 51 Mov [(r 32)]  [(m RetReg)])
    (OPER 52 Mov [(r 2)]  [(r 32)])
    (OPER 53 Mov [(r 34)]  [(i 48)])
    (OPER 54 Add_I [(r 33)]  [(r 34)(r 2)])
    (OPER 55 Pass []  [(r 33)])
    (OPER 56 JSR []  [(s putchar)])
    (OPER 57 Mov [(r 35)]  [(m RetReg)])
    (OPER 58 Mov [(r 36)]  [(i 56)])
    (OPER 59 Pass []  [(r 36)])
    (OPER 60 JSR []  [(s putchar)])
    (OPER 61 Mov [(r 37)]  [(m RetReg)])
    (OPER 62 Mov [(r 38)]  [(i 61)])
    (OPER 63 Pass []  [(r 38)])
    (OPER 64 JSR []  [(s putchar)])
    (OPER 65 Mov [(r 39)]  [(m RetReg)])
    (OPER 66 Add_I [(r 40)]  [(r 2)(r 3)])
    (OPER 67 Pass []  [(r 40)])
    (OPER 68 JSR []  [(s putchar)])
    (OPER 69 Mov [(r 41)]  [(m RetReg)])
    (OPER 70 Mov [(r 42)]  [(i 10)])
    (OPER 71 Pass []  [(r 42)])
    (OPER 72 JSR []  [(s putchar)])
    (OPER 73 Mov [(r 43)]  [(m RetReg)])
    (OPER 74 Mov [(r 44)]  [(i 0)])
    (OPER 75 Mov [(r 5)]  [(r 44)])
    (OPER 76 Mov [(r 46)]  [(i 10)])
    (OPER 77 LT [(r 45)]  [(r 5)(r 46)])
    (OPER 78 BEQ []  [(r 45)(i 0)(bb 10)])
  )
  (BB 9
    (OPER 79 Mov [(r 48)]  [(i 48)])
    (OPER 80 Add_I [(r 47)]  [(r 48)(r 5)])
    (OPER 81 Pass []  [(r 47)])
    (OPER 82 JSR []  [(s putchar)])
    (OPER 83 Mov [(r 49)]  [(m RetReg)])
    (OPER 84 Mov [(r 51)]  [(i 1)])
    (OPER 85 Add_I [(r 50)]  [(r 5)(r 51)])
    (OPER 86 Mov [(r 5)]  [(r 50)])
    (OPER 87 Mov [(r 53)]  [(i 10)])
    (OPER 88 LT [(r 52)]  [(r 5)(r 53)])
    (OPER 89 BNE []  [(r 52)(i 0)(bb 9)])
  )
  (BB 10
    (OPER 90 Mov [(r 54)]  [(i 10)])
    (OPER 91 Pass []  [(r 54)])
    (OPER 92 JSR []  [(s putchar)])
    (OPER 93 Mov [(r 55)]  [(m RetReg)])
    (OPER 94 Mov [(r 56)]  [(i 67)])
    (OPER 95 Pass []  [(r 56)])
    (OPER 96 JSR []  [(s putchar)])
    (OPER 97 Mov [(r 57)]  [(m RetReg)])
    (OPER 98 Mov [(r 58)]  [(i 83)])
    (OPER 99 Pass []  [(r 58)])
    (OPER 100 JSR []  [(s putchar)])
    (OPER 101 Mov [(r 59)]  [(m RetReg)])
    (OPER 102 Mov [(r 60)]  [(i 3510)])
    (OPER 103 Pass []  [(r 60)])
    (OPER 104 JSR []  [(s printInt)])
    (OPER 105 Mov [(r 61)]  [(m RetReg)])
    (OPER 106 Mov [(r 62)]  [(i 10)])
    (OPER 107 Pass []  [(r 62)])
    (OPER 108 JSR []  [(s putchar)])
    (OPER 109 Mov [(r 63)]  [(m RetReg)])
    (OPER 110 Mov [(r 64)]  [(i 0)])
    (OPER 111 Mov [(r 1)]  [(r 64)])
    (OPER 112 Mov [(r 65)]  [(i 1)])
    (OPER 113 Mov [(r 2)]  [(r 65)])
    (OPER 114 Mov [(r 66)]  [(i 1)])
    (OPER 115 Mov [(r 3)]  [(r 66)])
    (OPER 116 Mov [(r 67)]  [(i 0)])
    (OPER 117 Mov [(r 4)]  [(r 67)])
    (OPER 118 Mov [(r 68)]  [(i 0)])
    (OPER 119 Mov [(r 5)]  [(r 68)])
    (OPER 120 Mov [(r 70)]  [(i 0)])
    (OPER 121 EQ [(r 69)]  [(r 1)(r 70)])
    (OPER 122 BEQ []  [(r 69)(i 0)(bb 12)])
  )
  (BB 11
    (OPER 123 Mov [(r 72)]  [(i 0)])
    (OPER 124 EQ [(r 71)]  [(r 2)(r 72)])
    (OPER 125 BEQ []  [(r 71)(i 0)(bb 15)])
  )
  (BB 14
    (OPER 126 Mov [(r 73)]  [(i 1)])
    (OPER 127 Mov [(r 5)]  [(r 73)])
  )
  (BB 16
  )
  (BB 13
    (OPER 146 Mov [(r 83)]  [(i 10)])
    (OPER 147 EQ [(r 82)]  [(r 5)(r 83)])
    (OPER 148 BEQ []  [(r 82)(i 0)(bb 24)])
  )
  (BB 23
    (OPER 149 Mov [(r 84)]  [(i 99)])
    (OPER 150 Pass []  [(r 84)])
    (OPER 151 JSR []  [(s putchar)])
    (OPER 152 Mov [(r 85)]  [(m RetReg)])
    (OPER 153 Mov [(r 86)]  [(i 0)])
    (OPER 154 Pass []  [(r 86)])
    (OPER 155 JSR []  [(s putDigit)])
    (OPER 156 Mov [(r 87)]  [(m RetReg)])
    (OPER 157 Mov [(r 88)]  [(i 0)])
    (OPER 158 Pass []  [(r 88)])
    (OPER 159 JSR []  [(s putDigit)])
    (OPER 160 Mov [(r 89)]  [(m RetReg)])
    (OPER 161 Mov [(r 90)]  [(i 108)])
    (OPER 162 Pass []  [(r 90)])
    (OPER 163 JSR []  [(s putchar)])
    (OPER 164 Mov [(r 91)]  [(m RetReg)])
  )
  (BB 25
    (OPER 185 Mov [(r 101)]  [(i 10)])
    (OPER 186 Pass []  [(r 101)])
    (OPER 187 JSR []  [(s putchar)])
    (OPER 188 Mov [(r 102)]  [(m RetReg)])
    (OPER 189 Mov [(r 103)]  [(i 0)])
    (OPER 190 Mov [(m RetReg)]  [(r 103)])
    (OPER 191 Jmp []  [(bb 1)])
  )
  (BB 1
    (OPER 1 Func_Exit []  [])
    (OPER 2 Return []  [(m RetReg)])
  )
  (BB 5
    (OPER 12 Mov [(r 10)]  [(i 4)])
    (OPER 13 Store []  [(r 10)(s a)])
    (OPER 14 Jmp []  [(bb 6)])
  )
  (BB 21
    (OPER 138 Mov [(r 80)]  [(i 3)])
    (OPER 139 Mov [(r 5)]  [(r 80)])
    (OPER 140 Jmp []  [(bb 22)])
  )
  (BB 18
    (OPER 133 Mov [(r 78)]  [(i 0)])
    (OPER 134 EQ [(r 77)]  [(r 4)(r 78)])
    (OPER 135 BEQ []  [(r 77)(i 0)(bb 21)])
  )
  (BB 20
    (OPER 136 Mov [(r 79)]  [(i 10)])
    (OPER 137 Mov [(r 5)]  [(r 79)])
  )
  (BB 22
    (OPER 141 Jmp []  [(bb 19)])
  )
  (BB 15
    (OPER 128 Mov [(r 75)]  [(i 0)])
    (OPER 129 EQ [(r 74)]  [(r 3)(r 75)])
    (OPER 130 BEQ []  [(r 74)(i 0)(bb 18)])
  )
  (BB 17
    (OPER 131 Mov [(r 76)]  [(i 2)])
    (OPER 132 Mov [(r 5)]  [(r 76)])
  )
  (BB 19
    (OPER 142 Jmp []  [(bb 16)])
  )
  (BB 12
    (OPER 143 Mov [(r 81)]  [(i 0)])
    (OPER 144 Mov [(r 5)]  [(r 81)])
    (OPER 145 Jmp []  [(bb 13)])
  )
  (BB 24
    (OPER 165 Mov [(r 92)]  [(i 98)])
    (OPER 166 Pass []  [(r 92)])
    (OPER 167 JSR []  [(s putchar)])
    (OPER 168 Mov [(r 93)]  [(m RetReg)])
    (OPER 169 Mov [(r 94)]  [(i 97)])
    (OPER 170 Pass []  [(r 94)])
    (OPER 171 JSR []  [(s putchar)])
    (OPER 172 Mov [(r 95)]  [(m RetReg)])
    (OPER 173 Mov [(r 96)]  [(i 100)])
    (OPER 174 Pass []  [(r 96)])
    (OPER 175 JSR []  [(s putchar)])
    (OPER 176 Mov [(r 97)]  [(m RetReg)])
    (OPER 177 Mov [(r 98)]  [(i 61)])
    (OPER 178 Pass []  [(r 98)])
    (OPER 179 JSR []  [(s putchar)])
    (OPER 180 Mov [(r 99)]  [(m RetReg)])
    (OPER 181 Pass []  [(r 5)])
    (OPER 182 JSR []  [(s printInt)])
    (OPER 183 Mov [(r 100)]  [(m RetReg)])
    (OPER 184 Jmp []  [(bb 25)])
  )
)