package util;
import java.util.UUID;


    public abstract class IdGenerator {

        public static String generateId() {
            return UUID
                    .randomUUID()
                    .toString()
                    .split("-")[0];
        }


}
