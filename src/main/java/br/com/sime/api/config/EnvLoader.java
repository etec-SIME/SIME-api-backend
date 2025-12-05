//package br.com.sime.api.config;
//
//import io.github.cdimascio.dotenv.Dotenv;
//
//public class EnvLoader {
//    public static void loadEnv() {
//        Dotenv dotenv = Dotenv.load();
//
//        setIfPresent("AZURE_STORAGE_CONNECTION_STRING", dotenv);
//        setIfPresent("AZURE_URL", dotenv);
//        setIfPresent("AZURE_USER", dotenv);
//        setIfPresent("AZURE_PASSWORD", dotenv);
//        setIfPresent("JWT_SECRET", dotenv);
//    }
//
//    private static void setIfPresent(String key, Dotenv dotenv) {
//        String value = dotenv.get(key);
//        if (value != null) {
//            System.setProperty(key, value);
//        } else {
//            System.err.println("Variável " + key + " não encontrada no .env");
//        }
//    }
//}
