package GeneticAlgorithm.InstrumentCode;

/**
 * Thực hiện phân tích code
 */
public class MainInstrumentCode {
	static String relativePath = "..\\Final_AutomationTesting\\src\\";
	public static void main(String[] args) {
	
		String[] inputs = { "Example" };
		// instrument code, phân tích code và tạo chữ kí, path..(.path, .sign, .tgt)
		for (String str : inputs) {
			String[] srcfiles = {relativePath + str + ".oj" };
			openjava.ojc.Main.main(srcfiles);
		}
	}

}
