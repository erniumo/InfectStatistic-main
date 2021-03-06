/**
 * InfectStatistic
 * TODO
 *
 * @author xxx
 * @version xxx
 * @since xxx
 */
import java.io.*;
  
class InfectStatistic {
    public static String []province={"全国","安徽","北京","重庆","福建","甘肃","广东","广西","贵州","海南","河北","河南",
    "黑龙江","湖北","湖南","吉林","江苏","江西","辽宁","内蒙古","宁夏","青海","山东","山西","陕西","上海","四川","天津",
    "西藏","新疆","云南","浙江"};
    public static int [][]typ=new int[4][32];
    public static String basepath="C:\\Users\\lenovo\\Documents\\GitHub\\erniumo\\22171319\\log\\";
    public static String _log;
    public static String _out;
    public static String []_type={"ip","sp","cure","dead"};
    public static String []_type1={"感染患者","疑似患者","治愈","死亡"};
    public static int []ty=new int[4];
    public static String _date=null;
    public static int []pr=new int[32];
    public static int tempp=0;
    public static int tempt=0;
    public static void main(final String[] args) {
        for(int i=0;i<args.length;){
            if(args[i].equals("-log")){
                _log=args[i+1];
                i+=2;
                continue;
            }
            if(args[i].equals("-out")){
                _out=args[i+1];
                i+=2;
                continue;
            }
            if(args[i].equals("-date")){
                _date=args[i+1];
                i+=2;
                continue;
            }
            if(args[i].equals("-type")){
                tempt=1;
                int t=1;
                i++;
                while(t==1){
                    for(int j=0;j<ty.length;j++){
                        if(args[i].equals(_type[j])){
                            ty[j]=1;
                            t=1;
                            i++;
                            if(i==args.length)t=0;
                            break;
                        }
                        else t=0;
                    }
                }
                continue;
            }
            if(args[i].equals("-province")){
                tempp=1;
                int t=1;
                i++;
                while(t==1){
                    for(int j=0;j<province.length;j++){
                        if(args[i].equals(province[j])){
                            pr[j]=1;
                            t=1;
                            i++;
                            if(i==args.length)t=0;
                            break;
                        }
                        else t=0;
                    }
                }
                continue;
            }
            if(args[i].equals("list")){
                i++;
                continue;
            }
        }
        
        File file=new File(_log);
        File []filelist=file.listFiles();
        int n=filelist.length;
        if(_date==null){
            for(int i=0;i<n;i++){
                ReadFile(filelist[i].getPath());
            }
        }
        else{
            for(int i=0;i<n;i++){
                ReadFile(filelist[i].getPath());
                if(filelist[i].getPath().equals("D:\\log\\"+_date+".log.txt"))break;
               // if(filelist[i].getPath().equals(basepath+_date+".log.txt"))break;
            }
        }
        writeFile(_out);
    }
    public static int Provin(String name){
        for(int i=0;i<province.length;i++){
            if(name.equals(province[i]))return i;
        }
        return 0;
    }
    public static int Cuts(String s){
        char []ch=s.toCharArray();
        int k=0;
        for(int i=0;i<ch.length;i++){
            if(ch[i]<'0'||ch[i]>'9'){
                k=i;
                break;
            }
        }
      //  System.out.println(Integer.parseInt(s.substring(0, k)));
        return Integer.parseInt(s.substring(0, k));
    }

    public static void Adddel3(String[] ss,int n){
        String op=ss[1];
        int num=Cuts(ss[2]);
        if(op.equals("死亡")){
            typ[3][n]+=num;
            typ[0][n]-=num;
        }
        else if(op.equals("治愈")){
            typ[2][n]+=num;
            typ[0][n]-=num;
        }
    }

    public static void Adddel4(String []ss,int n){
        int num=Cuts(ss[3]);
        if(ss[1].equals("新增")){
            if(ss[2].equals("感染患者")){
                typ[0][n]+=num;
            }
            else if(ss[2].equals("疑似患者"))typ[1][n]+=num;
        }
        else if(ss[1].equals("疑似患者")&&ss[2].equals("确诊感染")){
            typ[0][n]+=num;
            typ[1][n]-=num;
        }
        else if(ss[1].equals("排除")&&ss[2].equals("疑似患者")){
            typ[1][n]-=num;
        }
    }

    public static void Adddel5(String []ss,int i1,int i2){
        int num=Cuts(ss[4]);
        if(ss[1].equals("疑似患者")){
            typ[1][i1]-=num;
            typ[1][i2]+=num;
        }
        else if(ss[1].equals("感染患者")){
            typ[0][i1]-=num;
            typ[0][i2]+=num;
        }
    }

    public static void ReadFile(String pathname) { 
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) 
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.toCharArray()[0]=='/')break;
                String []ss=line.split("\\s+");
                if(ss.length==3){
                   int i=Provin(ss[0]);
                   Adddel3(ss,i);
                }
                else if(ss.length==4){
                    int i=Provin(ss[0]);
                    Adddel4(ss,i);
                }
                else if(ss.length==5){
                    int i1=Provin(ss[0]);
                    int i2=Provin(ss[3]);
                    Adddel5(ss,i1,i2);
                }
            }
         //  for(int i=0;i<8;i++)System.out.println(typ[1][i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入TXT文件
     */
    public static void writeFile(String pathname) {
        try {
            String path=pathname;
            File writeName = new File(path); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                for(int i=1;i<province.length;i++){
                        typ[0][0]+=typ[0][i];
                        typ[1][0]+=typ[1][i];
                        typ[2][0]+=typ[2][i];
                        typ[3][0]+=typ[3][i];
                }
                for(int i=0;i<province.length;i++){
                    if(tempp==1){
                        if(pr[i]==1){
                            out.write(province[i]);
                            if(tempt==1){
                                for(int k=0;k<ty.length;k++){
                                    if(ty[k]==1){
                                        out.write(" "+_type1[k]+" "+typ[k][i]+"人");
                                    }
                                }
                            }
                            else{
                                for(int k=0;k<ty.length;k++){
                                    if(ty[k]==0){
                                        out.write(" "+_type1[k]+" "+typ[k][i]+"人");
                                    }
                                }
                            }
                            
                            out.write("\r\n");
                        }
                    }
                    else{
                        if(pr[i]==0){
                            out.write(province[i]);
                            if(tempt==1){
                                for(int k=0;k<ty.length;k++){
                                    if(ty[k]==1){
                                        out.write(" "+_type1[k]+" "+typ[k][i]+"人");
                                    }
                                }
                            }
                            else{
                                for(int k=0;k<ty.length;k++){
                                    if(ty[k]==0){
                                        out.write(" "+_type1[k]+" "+typ[k][i]+"人");
                                    }
                                }
                            }
                            
                            out.write("\r\n");
                        }   
                    }
                        
                }
                out.write("// 该文档并非真实数据，仅供测试使用\r\n");
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


}
