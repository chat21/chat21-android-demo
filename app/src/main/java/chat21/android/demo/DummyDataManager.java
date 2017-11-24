package chat21.android.demo;

import java.util.ArrayList;
import java.util.List;

import chat21.android.user.models.ChatUser;
import chat21.android.user.models.IChatUser;

/**
 * Created by stefanodp91 on 25/10/17.
 * <p>
 * Dummy data provider
 */

public class DummyDataManager {

    private static final int LOGGED_USER_ID = 0;

    public static IChatUser getLoggedUser() {
        PersonGenerator generator = new PersonGenerator();

        IChatUser loggedUser = convertPersonToChatUser(LOGGED_USER_ID,
                generator.getPeople().get(LOGGED_USER_ID));

        return loggedUser;
    }

    public static List<IChatUser> getContacts() {
        List<IChatUser> contacts = new ArrayList<>();

        PersonGenerator generator = new PersonGenerator();

        // starts from the second item.
        // the first one is the logged user
        for (int i = 1; i < generator.getPeople().size(); i++) {

            IChatUser chatUser = convertPersonToChatUser(i, generator.getPeople().get(i));

            contacts.add(chatUser);
        }

        return contacts;
    }

    private static IChatUser convertPersonToChatUser(int counter, PersonGenerator.Person person) {
        IChatUser chatUser = new ChatUser();

        chatUser.setEmail(person.getEmail());
        chatUser.setFullName(person.getName() + " " + person.getSurname());
        chatUser.setId(person.getName().toLowerCase() + "_" + person.getSurname().toLowerCase());
        chatUser.setProfilePictureUrl("https://randomuser.me/api/portraits/" + person.getGender() + "/" + counter + ".jpg");

        return chatUser;
    }

    private static class PersonGenerator {
        private enum Gender {
            women,
            men
        }

        private static final String[] mNames = {
                "Shanice",
                "Tatiana",
                "Mikki",
                "Lamar",
                "Laurice",
                "Carina",
                "Marlana",
                "Lizabeth",
                "Oralee",
                "Rupert",
                "Alysia",
                "Lonny",
                "Angelita",
                "Molly",
                "Cherlyn",
                "Kam",
                "Jed",
                "Deneen",
                "Irvin",
                "Kizzy"
        };

        private static final String[] mSurnames = {
                "Kantner",
                "Vanwingerden",
                "Quandt",
                "Haughton",
                "Hoadley",
                "Sala",
                "Bosworth",
                "Mongeau",
                "Clewis",
                "Remington",
                "Labrador",
                "Mcferren",
                "Benoit",
                "Kerlin",
                "Meadow",
                "Pusey",
                "Birkholz",
                "Vannatta",
                "Kerwin",
                "Lake"
        };

        private static final Gender[] mGenders = {
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.men,
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.men,
                Gender.women,
                Gender.men,
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.women,
                Gender.men,
                Gender.women,
                Gender.men,
                Gender.women,
        };


        private List<Person> getPeople() {
            List<Person> people = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                people.add(new Person(mNames[i], mSurnames[i], mGenders[i].toString()));
            }

            return people;
        }

        private static class Person {
            private String mName, mSurname, mGender;

            private Person(String name, String surname, String gender) {
                setName(name);
                setSurname(surname);
                setGender(gender);
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public String getSurname() {
                return mSurname;
            }

            public void setSurname(String surname) {
                mSurname = surname;
            }

            public String getGender() {
                return mGender;
            }

            public void setGender(String gender) {
                mGender = gender;
            }

            public String getEmail() {
                return getName() + "." + getSurname() + "@gmail.com";
            }

            @Override
            public String toString() {
                return "Person{" +
                        "name='" + mName + '\'' +
                        ", surname='" + mSurname + '\'' +
                        ", gender='" + mGender + '\'' +
                        '}';
            }
        }
    }
}