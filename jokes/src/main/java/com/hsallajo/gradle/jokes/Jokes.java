package com.hsallajo.gradle.jokes;
import java.util.Random;

public class Jokes {

    private static final String[] jokesArray = {
            "I asked God for a bike, but I know God doesn't work that way. So I stole a bike and asked for forgiveness."
            , "Do not argue with an idiot. He will drag you down to his level and beat you with experience."
            , "I want to die peacefully in my sleep, like my grandfather. Not screaming and yelling like the passengers in his car."
            , "We live in a society where pizza gets to your house before the police."
            , "Knowledge is knowing a tomato is a fruit. Wisdom is not putting it in a fruit salad."
            , "Children: You spend the first 2 years of their life teaching them to walk and talk. Then you spend the next 16 years telling them to sit down and shut-up."
            , "My mother never saw the irony in calling me a son-of-a-bitch."
            , "Men have two emotions: Hungry and Horny. If you see him without an erection, make him a sandwich."
            , "Evening news is where they begin with 'Good evening', and then proceed to tell you why it isn't."
            , "If you think nobody cares if you're alive, try missing a couple of payments."
            , "Did you know that dolphins are so smart that within a few weeks of captivity, they can train people to stand on the very edge of the pool and throw them fish?"
            , "I saw a woman wearing a sweat shirt with 'Guess' on it…so I said 'Implants?'"
            , "Why does someone believe you when you say there are four billion stars, but checks when you say the paint is wet?"
            , "A clear conscience is usually the sign of a bad memory."
            , "The shinbone is a device for finding furniture in a dark room."
            , "Laugh at your problems, everybody else does."
            , "Children in the back seats of cars cause accidents, but accidents in the back seats of cars cause children."
            , "Artificial intelligence is no match for natural stupidity."
            , "How many divorced men does it take to change a light bulb? -Who cares? They never get the house anyway."
            , "Why do men get married? So they don't have to hold-in their stomachs any more."};

    public static String getJoke() {
        Random rand = new Random();
        return jokesArray[rand.nextInt(jokesArray.length)];
    }
}