package RadixSort;
//Сортировка строки
public class SORT {
	private static int MaxLenght = 0;
	//Возвращение эл-та строки 
	static char getChar(String a, int i)
	{
		if (i < a.length())	return a.charAt(i); else return 0;
	}
	//Вывод всех строк на экран
	static void print(String[] result, int N)
	{
		for (int i = 0; i < N; ++i)
		{
			System.out.println(result[i]);
		}
		System.out.println();
	}
	//Функция сортировки по одному эл-ту
	static void radix(String[] tmp, String[] toHelp, int N, int digit)
	{
		//Кол-во эл-ов сравнения
		int constN = 65536;
		//Массивы статистики и индексов(см.cтр 8 - 9)
		int stat[] = new int [constN];
		int index[] = new int[constN];
		//Обнуление массивов
		for (int i = 0; i < constN; ++i)
		{
			stat[i] = 0;
			index[i] = 0;
		}
		//создание массива статистики
		for ( int i = 0; i < N; ++i )
			stat[(int)getChar( tmp[i], digit )]++;
		//Создание массива индексов
		for (int i = 1; i < constN; ++i)
		{
			index[i] = index[i-1] + stat[i-1];
		}
		//Перестановка местами эл-ов в отсортированном по digit эл-ту
		for (int i = 0; i < N; ++i)
		{
			char d = getChar(tmp[i], digit);
			toHelp[index[d]] = tmp[i];
			index[d]++;
		}
	}
	//Функция сортировки по всем эл-ам
	static void RadixSort(String[] result, String[] toHelp, int N)
	{
		//Создание временного массива
		String[] tmp = new String[N];
		for (int i = 0; i < N; ++i)
		{
			tmp[i] = result[i];
		}
		//сортировка по всем эл-ам
		for (int i = MaxLenght - 1; i >= 0; --i)
		{
			radix(tmp, toHelp, N, i);
			swap(tmp, toHelp, N);
		}
		swap(tmp, toHelp, N);
	}
	//Смена местами двух массивов
	static void swap(String[] a, String[] b, int N)
	{
		for (int i= 0; i < N; ++i)
		{
			String tmp = a[i];
			a[i] = b[i];
			b[i] = tmp;
		}
	}
	//Нахождение строки с максимальной длиной
	static void findMaxLenght(String[] a, int n)
	{
		for (int i = 0; i < n; ++i)
		{
			if (a[i].length() > MaxLenght) MaxLenght = a[i].length();
		}
	}

}
