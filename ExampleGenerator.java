package examples.generated;

// ExampleGenerator.java
// Generates many small Java 8 banking example files (streams, lambdas, Optionals, Date/Time, CompletableFuture, collectors, etc.)
//
// Usage:
//   javac ExampleGenerator.java
//   java ExampleGenerator <count> <outputDir>
// Example:
//   java ExampleGenerator 500 ./generated-examples
//
// Output: <outputDir>/examples/generated/ExampleBanking000001.java ... ExampleBanking000500.java

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExampleGenerator {
    private static final int DEFAULT_COUNT = 500;

    public static void main(String[] args) throws Exception {
        int count = DEFAULT_COUNT;
        File outBase;
        if (args.length >= 2) {
            count = Integer.parseInt(args[0]);
            outBase = new File(args[1], "examples/generated");
        } else if (args.length == 1) {
            count = Integer.parseInt(args[0]);
            outBase = new File("./generated-examples/examples/generated");
        } else {
            outBase = new File("./generated-examples/examples/generated");
        }

        if (!outBase.exists()) {
            if (!outBase.mkdirs()) {
                throw new IOException("Could not create output dir: " + outBase.getAbsolutePath());
            }
        }

        for (int i = 1; i <= count; i++) {
            int type = i % 10; // 10 template types
            String className = String.format("ExampleBanking%06d", i);
            File f = new File(outBase, className + ".java");
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"))) {
                writeHeader(pw, className);
                switch (type) {
                    case 0: writeStreamFilterExample(pw); break;
                    case 1: writeReduceExample(pw); break;
                    case 2: writeOptionalExample(pw); break;
                    case 3: writeComparatorExample(pw); break;
                    case 4: writeDateTimeExample(pw); break;
                    case 5: writeCompletableFutureExample(pw); break;
                    case 6: writeGroupingExample(pw); break;
                    case 7: writePartitioningFraudExample(pw); break;
                    case 8: writeCustomFunctionalInterfaceExample(pw); break;
                    case 9: writeCollectorJoiningExample(pw); break;
                    default: writeStreamFilterExample(pw); break;
                }
                writeFooter(pw);
            }
            if (i % 100 == 0) {
                System.out.println("Generated " + i + " examples...");
            }
        }
        System.out.println("Done. Generated " + count + " example files under " + outBase.getAbsolutePath());
    }

    private static void writeHeader(PrintWriter pw, String className) {
        pw.println("package examples.generated;");
        pw.println();
        pw.println("import java.util.*;");
        pw.println("import java.util.stream.*;");
        pw.println("import java.time.*;");
        pw.println("import java.time.format.DateTimeFormatter;");
        pw.println("import java.util.concurrent.*;");
        pw.println();
        pw.println("public class " + className + " {");
    }

    private static void writeFooter(PrintWriter pw) {
        pw.println("}");
    }

    // Template 0: Stream filtering transactions
    private static void writeStreamFilterExample(PrintWriter pw) {
        pw.println("    static class Transaction {");
        pw.println("        double amount;");
        pw.println("        String type; // \"debit\" or \"credit\"");
        pw.println("        Transaction(double a, String t){ this.amount=a; this.type=t; }");
        pw.println("        public String toString(){ return type+":"+amount; }");
        pw.println("    }");
        pw.println();
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Transaction> txs = Arrays.asList(");
        pw.println("            new Transaction(120.50, \"debit\"),");
        pw.println("            new Transaction(250.00, \"credit\"),");
        pw.println("            new Transaction(75.25, \"debit\")");
        pw.println("        );");
        pw.println("        // filter debit transactions using streams and lambda");
        pw.println("        List<Transaction> debits = txs.stream()");
        pw.println("            .filter(t -> \"debit\".equals(t.type))");
        pw.println("            .collect(Collectors.toList());");
        pw.println("        System.out.println(\"Debits: \" + debits);\n");
        pw.println("    }");
    }

    // Template 1: Reduce total balance
    private static void writeReduceExample(PrintWriter pw) {
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Double> deposits = Arrays.asList(1000.0, 2000.0, 1500.0);\n");
        pw.println("        // compute total using reduce and method reference");
        pw.println("        double total = deposits.stream()");
        pw.println("            .reduce(0.0, Double::sum);\n");
        pw.println("        System.out.println(\"Total deposits: \" + total);\n");
        pw.println("    }");
    }

    // Template 2: Optional balance handling
    private static void writeOptionalExample(PrintWriter pw) {
        pw.println("    static class Account {");
        pw.println("        String id; Double balance;");
        pw.println("        Account(String id, Double b){ this.id=id; this.balance=b; }");
        pw.println("    }");
        pw.println();
        pw.println("    public static void main(String[] args){");
        pw.println("        Account a = new Account(\"A-100\", null);\n");
        pw.println("        // use Optional to provide fallback");
        pw.println("        Double safeBalance = Optional.ofNullable(a.balance).orElse(0.0);\n");
        pw.println("        System.out.println(\"Safe balance for \" + a.id + " = " + safeBalance);\n");
        pw.println("    }");
    }

    // Template 3: Comparator and sorting with lambda
    private static void writeComparatorExample(PrintWriter pw) {
        pw.println("    static class Account {");
        pw.println("        String name; double balance;");
        pw.println("        Account(String n, double b){ this.name=n; this.balance=b; }");
        pw.println("        public String toString(){ return name+":"+balance; }");
        pw.println("    }");
        pw.println();
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Account> accounts = Arrays.asList(");
        pw.println("            new Account(\"Alice\", 2500.0),");
        pw.println("            new Account(\"Bob\", 1500.0),");
        pw.println("            new Account(\"Charlie\", 3000.0)");
        pw.println("        );");
        pw.println("        accounts.sort((a1, a2) -> Double.compare(a2.balance, a1.balance));\n");
        pw.println("        accounts.forEach(a -> System.out.println(a));\n");
        pw.println("    }");
    }

    // Template 4: Date and time for transactions
    private static void writeDateTimeExample(PrintWriter pw) {
        pw.println("    public static void main(String[] args){");
        pw.println("        LocalDateTime now = LocalDateTime.now();\n");
        pw.println("        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\");\n");
        pw.println("        System.out.println(\"Transaction timestamp: \" + now.format(fmt));\n");
        pw.println("    }");
    }

    // Template 5: CompletableFuture simple async credit scoring (simulated)
    private static void writeCompletableFutureExample(PrintWriter pw) {
        pw.println("    static class Customer { String id; Customer(String id){this.id=id;} }");
        pw.println("    public static void main(String[] args) throws Exception {");
        pw.println("        Customer c = new Customer(\"C-500\");\n");
        pw.println("        CompletableFuture<Integer> scoreFuture = CompletableFuture.supplyAsync(() -> {");
        pw.println("            // simulate external scoring call\n");
        pw.println("            try{ Thread.sleep(100); } catch (InterruptedException e){}\n");
        pw.println("            return 720; // pretend credit score\n");
        pw.println("        });\n");
        pw.println("        scoreFuture.thenAccept(score -> System.out.println(\"Credit score for \" + c.id + " = " + score));\n");
        pw.println("        scoreFuture.get();\n");
        pw.println("    }");
    }

    // Template 6: groupingBy example (group transactions by type)
    private static void writeGroupingExample(PrintWriter pw) {
        pw.println("    static class Transaction { double amount; String type; Transaction(double a, String t){this.amount=a;this.type=t;} }");
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Transaction> txs = Arrays.asList(new Transaction(100,\"debit\"), new Transaction(200,\"credit\"), new Transaction(50,\"debit\"));\n");
        pw.println("        Map<String, Double> sumByType = txs.stream().collect(Collectors.groupingBy(t->t.type, Collectors.summingDouble(t->t.amount)));\n");
        pw.println("        System.out.println(\"Sum by type: \" + sumByType);\n");
        pw.println("    }");
    }

    // Template 7: partitioningBy for basic fraud detection heuristic
    private static void writePartitioningFraudExample(PrintWriter pw) {
        pw.println("    static class Transaction { double amount; String id; Transaction(String id,double a){this.id=id;this.amount=a;} public String toString(){return id+":"+amount;} }");
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Transaction> txs = Arrays.asList(new Transaction(\"t1\",50), new Transaction(\"t2\",5000), new Transaction(\"t3\",75));\n");
        pw.println("        Map<Boolean, List<Transaction>> suspicious = txs.stream().collect(Collectors.partitioningBy(t->t.amount>1000));\n");
        pw.println("        System.out.println(\"Suspicious: \" + suspicious.get(true));\n");
        pw.println("    }");
    }

    // Template 8: custom functional interface for transfers
    private static void writeCustomFunctionalInterfaceExample(PrintWriter pw) {
        pw.println("    @FunctionalInterface");
        pw.println("    interface Transfer { boolean move(String from, String to, double amount); }");
        pw.println("    public static void main(String[] args){");
        pw.println("        Transfer t = (from,to,amt) -> { System.out.println(\"Transfer \"+amt+\" from \"+from+\" to \"+to); return true; };\n");
        pw.println("        t.move(\"A\",\"B\",250.0);\n");
        pw.println("    }");
    }

    // Template 9: collectors joining example to create statement summary
    private static void writeCollectorJoiningExample(PrintWriter pw) {
        pw.println("    static class Entry { String desc; double amount; Entry(String d,double a){this.desc=d;this.amount=a;} public String toString(){return desc+":"+amount;} }");
        pw.println("    public static void main(String[] args){");
        pw.println("        List<Entry> entries = Arrays.asList(new Entry(\"deposit\",100.0), new Entry(\"fee\",-5.0));\n");
        pw.println("        String summary = entries.stream().map(e->e.toString()).collect(Collectors.joining(\", \"));\n");
        pw.println("        System.out.println(\"Statement: \" + summary);\n");
        pw.println("    }");
    }
}