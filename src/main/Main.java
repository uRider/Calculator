package main;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main  {
    static boolean onlyFrom0to10=true;
    static boolean only2digit=true;

    enum RomArab { Arab(1),Rom(2),NoElse(0),Err(-1);
        private int val;
        RomArab(int v) { this.val=v;       }
    }
    public static RomArab tipRA;

    enum Action{
        none,Plus,Minus,Div,Mult,Eqv,Err;
    }
    public static Action action=Action.none;

    public static void main(String[] args) throws IOException,NoValidateTipExeption {

        System.out.println("Введите выражение:");

        Scanner sc=new Scanner(System.in);
        try {
            Pattern pattern=Pattern.compile("(?:([0-9]+)|([IVXLDMC]+))(\\s*[*/+-]\\s*)?+");
            while (sc.hasNext()) {
                int cntDig=0;
                action=Action.none;
                String inputs=sc.nextLine();
                Matcher matcher=pattern.matcher(inputs);
                Expression exp=new Expression();
                tipRA=RomArab.NoElse;
                while (matcher.find()) {

                    int ival=-1;
                    if (matcher.start(1)>=0) {
                        String si=matcher.group(1);

                        if (tipRA ==RomArab.Rom) {
                            throw new NoValidateTipExeption("НЕ РИМСКАЯ ЦИФРА:"+si);
                        }
                        tipRA=RomArab.Arab;
                        cntDig++;
                        ival=StringParser.inputToInt(si);
                        if (onlyFrom0to10 && (ival< 0 || ival>10)) throw new NoValidateTipExeption("Только от 0 до 10ти");

                    }
                    if (matcher.start(2)>=0) {
                        String si=matcher.group(2);

                        if (tipRA ==RomArab.Arab) {
                            throw new NoValidateTipExeption("НЕ АРАБСКАЯ ЦИФРА:"+si);
                        }
                        tipRA=RomArab.Rom;
                        cntDig++;
                        ival=StringParser.inputToInt(si);
                        if (onlyFrom0to10 && (ival< 0 || ival>10)) throw new NoValidateTipExeption("Только от 0 до 10ти");

                    }
                    if (matcher.start(3)>=0) {
                        String sz = matcher.group(3).trim();  //знак

                        action = StringParser.toAction(sz);
                    }

                    if(cntDig>1) {
                        if (only2digit && cntDig>2 ) throw new NoValidateTipExeption("Только 2 числа");
                        if(action==Action.none) throw new NoValidateTipExeption("НЕ БЫЛО ЗНАКА ОПЕРАЦИИ:");
                        else if(only2digit) {action = Action.Eqv;}
                    }

                    exp.calc(ival,action);

                }
                System.out.println("Результат="+exp.getiRez());

            }
        } catch (Exception | NoValidateTipExeption e) {
            System.out.println("Неверный формат, "+e.getMessage());
        }
        sc.close();

    }
}