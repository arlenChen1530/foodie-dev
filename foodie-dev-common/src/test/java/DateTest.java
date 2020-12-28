import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTest {

    public static void main(String[] args) {
       int[]  yList = {2007,2020,2021,2022};
        for(int  i : yList){
            String yStr = i+"";
            if(i == 2007){
                 System.out.println("开始" +yStr+"年======================");
                getParentCode("2007-01-01");
                getParentCode("2006-12-31");
                 System.out.println("结束" +yStr+"年======================");
            }
            if(i == 2020){
                 System.out.println("开始" +yStr+"年======================");
                for(int y = 20;y<32;y++){
                    getParentCode(yStr+"-12-"+y);
                }
                 System.out.println("结束" +yStr+"年======================");
            }
            if(i == 2021){
                 System.out.println("开始" +yStr+"年======================");
                for(int x = 1;x<17;x++){
                    if(x<10){
                        getParentCode(yStr+"-01-0"+x);
                    }else{
                        getParentCode(yStr+"-01-"+x);
                    }
                }

                for(int z = 20;z<32;z++){
                    getParentCode(yStr+"-12-"+z);
                }
                 System.out.println("结束" +yStr+"年======================");
            }
            if(i == 2022){
                 System.out.println("开始" +yStr+"年======================");
                for(int c = 1;c<17;c++){
                    if(c<10){
                        getParentCode(yStr+"-01-0"+c);
                    }else{
                        getParentCode(yStr+"-01-"+c);
                    }
                }

                 System.out.println("结束" +yStr+"年======================");
            }
        }
    }
    static void getParentCode(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        System.out.println(dateStr+"====>>>"+localDate);
        String text =localDate.format(DateTimeFormatter.ISO_WEEK_DATE);
        System.out.println(dateStr+"====>>>ISO_WEEK_DATE=====>>>>"+text);
    }
}
