package RadixSort;
//���������� ������
public class SORT {
	private static int MaxLenght = 0;
	//����������� ��-�� ������ 
	static char getChar(String a, int i)
	{
		if (i < a.length())	return a.charAt(i); else return 0;
	}
	//����� ���� ����� �� �����
	static void print(String[] result, int N)
	{
		for (int i = 0; i < N; ++i)
		{
			System.out.println(result[i]);
		}
		System.out.println();
	}
	//������� ���������� �� ������ ��-��
	static void radix(String[] tmp, String[] toHelp, int N, int digit)
	{
		//���-�� ��-�� ���������
		int constN = 65536;
		//������� ���������� � ��������(��.c�� 8 - 9)
		int stat[] = new int [constN];
		int index[] = new int[constN];
		//��������� ��������
		for (int i = 0; i < constN; ++i)
		{
			stat[i] = 0;
			index[i] = 0;
		}
		//�������� ������� ����������
		for ( int i = 0; i < N; ++i )
			stat[(int)getChar( tmp[i], digit )]++;
		//�������� ������� ��������
		for (int i = 1; i < constN; ++i)
		{
			index[i] = index[i-1] + stat[i-1];
		}
		//������������ ������� ��-�� � ��������������� �� digit ��-��
		for (int i = 0; i < N; ++i)
		{
			char d = getChar(tmp[i], digit);
			toHelp[index[d]] = tmp[i];
			index[d]++;
		}
	}
	//������� ���������� �� ���� ��-��
	static void RadixSort(String[] result, String[] toHelp, int N)
	{
		//�������� ���������� �������
		String[] tmp = new String[N];
		for (int i = 0; i < N; ++i)
		{
			tmp[i] = result[i];
		}
		//���������� �� ���� ��-��
		for (int i = MaxLenght - 1; i >= 0; --i)
		{
			radix(tmp, toHelp, N, i);
			swap(tmp, toHelp, N);
		}
		swap(tmp, toHelp, N);
	}
	//����� ������� ���� ��������
	static void swap(String[] a, String[] b, int N)
	{
		for (int i= 0; i < N; ++i)
		{
			String tmp = a[i];
			a[i] = b[i];
			b[i] = tmp;
		}
	}
	//���������� ������ � ������������ ������
	static void findMaxLenght(String[] a, int n)
	{
		for (int i = 0; i < n; ++i)
		{
			if (a[i].length() > MaxLenght) MaxLenght = a[i].length();
		}
	}

}
