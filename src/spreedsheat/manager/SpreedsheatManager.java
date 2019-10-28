public class SpreedsheatManager
{
    public SpreedsheatManager()
    {
        super();
    }

    private String[]list = new String [256];
    static final int MINSIZE = 0;
    static final int MAXSIZE = 255;
    private int arrSize = 0;
    //NOTE arrSize is one ahead so if using it for counting, always -1 from it

    public boolean add()
    {
        int index = arrSize;
        if (arrSize >= MAXSIZE)
        {
            return false;
        }
        index++;
        String name = "sheet"+index;

        if ((length()) == 0)//this is so first entry always works
        {
            list[--index] = name;
            arrSize++;
            return true;
        }
        else if(index(name) != -1)//if in list then return false
        {
            return false;
        } //so I do not add duplicates


        list[--index] = name;
        arrSize++;
        return true;
    }

    public int remove(String sheetName)
    {
        if (arrSize >=1)
            return -1;
        int i;
        for(i=0; i< arrSize -1; i++)
        {
            if (list[i].compareToIgnoreCase(sheetName) == 0)
            {
                for(int x = i; x< arrSize -1; x++)
                {
                    list[x] = list[x+1]; //shift all other elements to the left
                    //used x+1 so it does not increment x twice
                    //and length -1 so we dont go over the array
                    if((x+1) == arrSize-1)
                        list[x+1] = null;
                }
                arrSize--;
                return i;
            }
        }
        return -1;
    }

    public String remove(int index)
    {
        if (arrSize <= 1 || index < 0)
            return null;

        else
        {
            String name = list[index];

            for(;index < arrSize -1; index++)
            {
                list[index] = list[index+1];
                if((index+1) == arrSize-1)
                    list[index+1] = null;
            }
            arrSize--;
            return name;
        }
    }

    public int move(String from, String to, boolean before)
    {
        boolean fromFlag = true;
        boolean toFlag = true;
        int f =0,t=0,i=0;

        if(from.equals(to))
            return -1;
        for (; i< arrSize; i++) //check if from is in the list
        {
            if(list[i].compareToIgnoreCase(from) == 0 && (fromFlag == true))
            {
               fromFlag = false;
               f = i;
            }
            if(list[i].compareToIgnoreCase(to) == 0 && (toFlag == true))
            {
                toFlag = false;
                t = i;
            }
        }

        if (fromFlag == false && toFlag == false)
            {
                //both strings exists so now move them
                if (before == true)
                {
                    for(int x=f;x<t-1;x++)
                    {
                        list[x]=list[x+1];
                        if ((x+1) == t-1)
                            list[x+1]=from;
                    }
                    //move from before to
                    return f;
                }
                else
                {
                    for(int x=f;x<t;x++)
                    {
                        list[x]=list[x+1];
                        if ((x+1) == t)
                            list[x+1]=from;
                    }
                    //move from after to
                    return f;

                }
            }
        else
            {
                return -1;
            }
    }

    public String move(int from, int to, boolean before)
    {
        String f = null, t = null;

        if(from != to && from >=0 && from <= arrSize && to <= arrSize && to >=0)
        {
            //both strings exists so now move them
            if (before == true)
            {
                String tmp = list[from];
                for(int x=from;x<to-1;x++)
                {
                    list[x]=list[x+1];
                    if ((x+1) == (to-1))
                        list[x+1]=tmp;
                }
                //move from before to
                return tmp;
            }
            else
            {
                String tmp = list[from];
                for(int x=from;x<to;x++)
                {
                    list[x]=list[x+1];
                    if ((x+1) == to)
                        list[x+1]=tmp;
                }
                //move from after to
                return tmp;
            }
        }

        else
        {
            return null;
        }
    }

    public String moveToEnd(int from)
    {
        if (from >=0 && from <= arrSize)
        {
            String name =list[from];

            for(int i =from; i<arrSize-1; i++)
            {
                list[i] = list[i+1];
            }
            list[arrSize-1] = name;
            return name;
        }
        else
        {
            return null;
        }

    }

    public int moveToEnd(String from)
    {
        int index = index(from);//tired of writing the same code everytime
        if (index != -1)
        {
            for(int i =index; i<arrSize-1; i++)
            {
                list[i] = list[i+1];
            }
            list[arrSize-1] = from;
            return index;
        }
        else
        {
            return -1;
        }
    }


    public int index(String sheetName)
    {
        int i=0;

        for(;i<= arrSize -1; i++)//using <= so it can acess the last element.
        //did not use it for move becuase it would have overwritten the element
        {
            if (list[i].compareToIgnoreCase(sheetName) == 0)
            {
                return i;
            }
        }
        return -1;
    }

    public String sheetName(int index)
    {
        if (index >=MINSIZE && index <= MAXSIZE)
        {
            return list[index];
        }
        return null;
    }

    public int length()
    {
        return arrSize;
    }

    public int rename(String currentName, String newName)
    {
        int curr = index(currentName);
        int now = index(newName);
        if ( (curr != -1) && (now == -1))
        {
            list[curr] = newName;
            return curr;
        }
        return -1;
    }


    public void display()
    {
        for (String content: list)
        {
            if (content != null) //did this because i dont want to see the other empty variables
                System.out.printf("%s%n",content);
        }
    }

    public static void main(String[] args)
    {
        SpreedsheatManager code = new SpreedsheatManager();
        for (int i =0; i< 7; i++)
        {
            code.add();
        }
        System.out.println("Original List....");
        code.display();

        System.out.println("\n"+"index that was moved was "+code.move("sheet1","sheet5",true));
        code.display();

        System.out.format("%nString moved was: %s",code.move(2,4,true));
        code.display();

        System.out.println("\n"+"Removed String: "+code.remove(1));
        code.display();

        System.out.format("%nMove to end. String moved: %s",code.moveToEnd(2));
        code.display();

        System.out.format("%nMove to end. Index moved: %s",code.moveToEnd("sheet2"));
        code.display();

        System.out.format("%n%nRenamed Content in index: %d%n",code.rename("sheet6","sheet17"));
        code.display();

        System.out.format("%nSheetname at index %d is: %s%n",5,code.sheetName(5));

    }
}
