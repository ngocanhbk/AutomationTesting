/*
 * Copyright(C) 2019 NgocAnh
 *
 * Chromosome.java, Sep 26, 2019 NgocAnh
 */
package GeneticAlgorithm.GA;

import java.util.*;

/**
 * Biểu diễn chromosome. Chromosome mã hóa thông tin của testcase
 * 
 * Examples:
 * 
 * $x0=A(int[0;1]):$x1=B#null:$x0.m(int,$x1)@1,88
 * $x0=A():$x1=B(int[-2;2]):$x1.g1():$x0.m(int,$x1)@-1,42
 */
public class Chromosome implements Comparable, Cloneable {
	/**
	 * Testcase : List hàm hoặc constructor
	 */
	private List actions = new LinkedList();

	/**
	 * Branch targets được bao phủ
	 */
	Collection coveredBranchTargets;

	/**
	 * Đường dẫn được bao phủ bởi TestCaseExecutor.
	 */
	public void setCoveredBranches(Set pathPoints) {
		coveredBranchTargets = pathPoints;
	}

	/**
	 * Branch targets được bao phủ ( chỉ xét trong method hiện tại)
	 */
	Collection target;

	/**
	 * Target được thỏa mãn bởi TestCaseExecutor.
	 */
	public void setCoveredTarget(Set pathPoints) {
		target = pathPoints;
	}

	/**
	 * Số branch được bao phủ đến hiện tại bởi chromosome
	 */
	int fitness = 0;

	/**
	 * kết quả trả về của method
	 */
	public String expectResult;

	/**
	 * Implements chromosome duplication.
	 */
	public Object clone() {
		List acts = new LinkedList();
		Iterator it = actions.iterator();
		while (it.hasNext()) {
			Action act = (Action) it.next();
			acts.add(act.clone());
		}
		return new Chromosome(acts);
	}

	/**
	 * Sắp xếp chromosome dựa trên độ giảm giá trị fitness
	 */
	public int compareTo(Object o) {
		Chromosome id = (Chromosome) o;
		return id.fitness - fitness;
	}

	/**
	 * Equality of chromosomes is based on fitness.
	 */
	public boolean equals(Object o) {
		Chromosome id = (Chromosome) o;
		return fitness == id.fitness;
	}

	/**
	 * get Fitness
	 */
	public int getFitness() {
		return fitness;
	}

	/**
	 * get Action
	 */
	public List getActions() {
		return actions;
	}

	/**
	 * số lượng action
	 */
	public int size() {
		return actions.size();
	}

	/**
	 * Gets ConstructorInvocation
	 *
	 * @param objId
	 *            object Target của constructor.
	 * @return ConstructorInvocation object của objId .
	 */
	private Action getConstructor(String objId) {
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			if (objId.equals(act.getObject()))
				return act;
		}
		return null;
	}

	/**
	 * xậy dựng chromosome từ các action
	 */
	public Chromosome(List acts) {
		actions = acts;
	}

	/**
	 * Builds chromosome.
	 */
	public Chromosome() {
	}

	/**
	 * biểu diễn chromosome
	 *
	 * Example:
	 * 
	 * <pre>
	 * $x0=A():$x1=B(int):$x1.c():$x0.m(int, $x1) @ 1, 4
	 * </pre>
	 */
	public String toString() {
		String s = "";
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			if (s.equals(""))
				s = act.actionDescription();
			else
				s += ":" + act.actionDescription();
		}
		s += "@";
		i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			String actVals = act.actualValues();
			if (!actVals.equals("")) {
				if (s.endsWith("@"))
					s += actVals;
				else
					s += "," + actVals;
			}
		}
		return s;
	}

	/**
	 * Lấy ra list các value của chromosome
	 * 
	 * @return actualParams List các value
	 */
	public List getActualValues() {
		List actualParams = new LinkedList();
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			String actVals = act.actualValues();
			if (!actVals.equals("")) {
				actualParams.add(act);
			}
		}
		return actualParams;

	}

	/**
	 * Lấy ra list các value của chromosome
	 * 
	 * @return actualParams List các value
	 */
	public String[] getListActualValues() {
		String[] actualParams = null;
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			String actVals = act.actualValues();
			if (!actVals.equals("")) {
				actualParams = actVals.split(",");
			}
		}
		return actualParams;
	}

	/**
	 * Set giá trị value cho action
	 * 
	 * @param newValue
	 *            list giá trị mới
	 */
	public void setInputValue(List newValue) {

		int valNum = 0;
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			act.setParameterValuesMethod(newValue);
		}
	}

	/**
	 * java code representation of Chromosome.
	 *
	 * Example:
	 * 
	 * <pre>
	 * $x0=A():$x1=B(int):$x1.c():$x0.m(int, $x1) @ 1, 4
	 * </pre>
	 * 
	 * becomes:
	 * 
	 * <pre>
	 * A x0 = new A();
	 * B x1 = B(1);
	 * x1.c();
	 * x0.m(4, x1) @ 1, 4
	 * </pre>
	 */
	public String toCode() {
		String s = "";
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			act.expectResult = expectResult;
			s += act.toCode() + "\n";
		}
		return s;
	}

	/**
	 * Xác định biến $xN được gán cho obj của 1 class từ class đã biết
	 *
	 * Scan chromosome cho tới khi gặp đối tượng của 1 class. left hand side
	 * variable được trả về
	 * 
	 * @param className
	 *            class của đối tượng tìm kiếm
	 * @return String đại diện của biến đối tượng tìm kiếm (hoặc null)
	 */
	public String getObjectId(String className) {
		if (className.indexOf("[") != -1)
			className = className.substring(0, className.indexOf("["));
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action a = (Action) i.next();
			if (className.equals(a.getName()))
				return a.getObject();
		}
		return null;
	}

	/**
	 * Xác định biến $xN được gán cho obj của 1 class từ list class đã biết
	 *
	 * @param classes
	 *            Danh sách các lớp đối tượng có thể thuộc về
	 * @return String đại diện của biến đối tượng tìm kiếm (hoặc null)
	 */
	public String getObjectId(List classes) {
		Iterator i = classes.iterator();
		while (i.hasNext()) {
			String cl = (String) i.next();
			String objId = getObjectId(cl);
			if (objId != null)
				return objId;
		}
		return null;
	}

	/**
	 * Adds action để mô tả input
	 * 
	 * @param act
	 *            Action được add
	 */
	public void addAction(Action act) {
		actions.add(act);
	}

	/**
	 * Ghép 2 chrom với nhau
	 *
	 * Example: $x0=A(int)@10 $x1.m($x0,int)@21
	 *
	 * $x0=A(int):$x1.m($x0,int)@10,21
	 *
	 * @param chrom
	 *            Chromosome sau khi được ghép
	 */
	public void append(Chromosome chrom) {
		actions.addAll(chrom.actions);
	}

	/**
	 * Mutation operator: thay đổi ngẫu nhiên 1 trong các value
	 */
	public void mutation() {
		int valNum = 0;
		Iterator i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			valNum += act.countPrimitiveTypes();
		}
		if (valNum == 0)
			return;
		int inputIndex = ChromosomeFormer.randomGenerator.nextInt(valNum);
		int k = 0;
		i = actions.iterator();
		while (i.hasNext()) {
			Action act = (Action) i.next();
			int actValNum = act.countPrimitiveTypes();
			if (k <= inputIndex && k + actValNum > inputIndex) {
				act.changeInputValue(inputIndex - k);
				break;
			}

			k += actValNum;
		}

	}

}