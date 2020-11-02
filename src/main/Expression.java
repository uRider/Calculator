package main;

public class Expression  {
    private  Integer iRez;
    private  Main.Action action;



    public  int calc(int ival, Main.Action operation) throws NoValidateTipExeption {
        if (iRez==null){
            iRez=ival;
        } else {
            switch (action) {
                case Plus:
                    iRez+=ival;
                    break;
                case Minus:
                    iRez-=ival;
                    break;
                case Mult:
                    iRez =iRez*ival;
                    break;
                case Div:
                    iRez=(Integer) iRez/ival;
                    break;
                case none:
                    throw new NoValidateTipExeption("НЕТ ОПЕРАЦИИ");
                default:
                    throw new NoValidateTipExeption("НЕТ ОПЕРАЦИИ");
            }


        }
        action=operation;

        return iRez;
    }

    public  String getiRez() throws NoValidateTipExeption {
        return StringParser.rezToString(iRez);
    }
}
