package com.fighter.tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caihaijun@navercorp.com on 2018/6/11.
 */
public class CodeUtil {

    public static String byteCodeToList(byte []  code,ClassObject cls){



        return "";
    }

    public static void main(String[] args) {
        //simple test cases.
        System.out.println(splitMethodDesc("(II)Ljava/lang/String;"));
        System.out.println(splitMethodDesc("Ljava/lang/String;BBBBLjava/lang/String;"));
        System.out.println(splitMethodDesc("ZBCSIFDJ[Z[B[C[S[I[F[D[JLZBCSIFDJ;LZBCSIFDJ;[LZBCSIFDJ;LZBCSIFDJ;[LZBCSIFDJ;"));
    }

    public static List<String> splitMethodDesc(String desc) {

        List<String> ret = new LinkedList<>();

        //\[*L[^;]+;|\[[ZBCSIFDJ]|[ZBCSIFDJ]
        int beginIndex = desc.indexOf('(');
        int endIndex = desc.lastIndexOf(')');
        if((beginIndex == -1 && endIndex != -1) || (beginIndex != -1 && endIndex == -1)) {
            System.err.println(beginIndex);
            System.err.println(endIndex);
            throw new RuntimeException();
        }
        String x0;
        if(beginIndex == -1 && endIndex == -1) {
            x0 = desc;
        }
        else {
            x0 = desc.substring(beginIndex + 1, endIndex);
        }
        Pattern pattern = Pattern.compile("\\[*L[^;]+;|\\[[ZBCSIFDJ]|[ZBCSIFDJ]");
        Matcher matcher = pattern.matcher(x0);

        ArrayList<String> listMatches = new ArrayList<String>();

        while(matcher.find())
        {
            listMatches.add(matcher.group());
        }

        for(String s : listMatches)
        {
            ret.add(s);
        }
        return ret;
    }

}
