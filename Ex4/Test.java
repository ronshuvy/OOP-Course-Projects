
public class Test {

	public static void main(String[] args){

		OpenHashSet openSet = new OpenHashSet(0.7f,0.1f);
		ClosedHashSet closedSet = new ClosedHashSet(0.7f,0.1f);

		openSet.add("1");
		openSet.add("2");
		openSet.add("3");
		System.out.println("Capacity, Size" + openSet.capacity() + ", " + openSet.size());
		openSet.add("4");
		System.out.println("Capacity, Size" + openSet.capacity() + ", " + openSet.size());
		openSet.add("5");
		openSet.add("6");
		openSet.add("7");
		openSet.add("8");
		openSet.add("9");
		openSet.add("10");
		openSet.add("11");
		System.out.println("Capacity, Size " + openSet.capacity() + ", " + openSet.size());
		openSet.add("12");
		System.out.println("Capacity, Size " + openSet.capacity() + ", " + openSet.size());
		openSet.add("1");
		System.out.println("Capacity, Size " + openSet.capacity() + " , " + openSet.size());
		openSet.add("18");
		openSet.add("13");
		openSet.add("14");
		openSet.add("15");
		openSet.add("16");
		openSet.add("17");
		System.out.println("Capacity, Size " + openSet.capacity() + ", " + openSet.size());


		/*
		 * Create set with upper and lower load factors of 0.7, 0.1 respectively.
		 * Add 3 Strings and check the set's size and capacity.
		 * Add 1 String and check the set's size.
		 * Add 7  Strings and checks the set's capacity.
		 * Add 1 String and check the set's capacity.
		 * Add a String already in the set.
		 * Add 6 new Strings. Check the set's size and capacity.):
		 * line number 19: expected:<32> but was:<16>
		 */





	}
}
