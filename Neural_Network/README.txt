Author : Soumya Batra
Description: This project simulates a Biological neural network using OOP concepts in Java


The Neural simulation is built with the follwing properties:

1. The user can enter the minimum number of neurons in the network.

2. Each neuron can be linked to a maximum of 3 other neurons.

3. At a particular depth(level with respect to the source Neuron), there can be a maximum of 5 Neurons.

4. A signal stays in a Neuron for maximum 7 time steps (corresponding to 7 ms) with time divided as:

    4.1  From 0-1 time steps, stimulus is evaluated for its requisite strength

    4.2  From 3-5 time steps, a Neuron is in its Action Potential Stage in case incoming signal is strong

    4.3  From 6-7 time steps, a Neuron is in its Refractory period.

5. We have taken 5/6 probability of a Neuron link having an excitatory neurotransmitter and 1/6 probability for an inhibitory neurotransmitter

6. For each time step, weights are calculated for al neurons at each depth before moving to neurons at next depth level

7. The source Neuron propagates the signal a time step = 2, since from 0-2 time steps, it is in the process of reaching threshold potential

8. The table beside the neural net lists the state of all Neurons at each time step

9. For a time step > 0, when all Neurons are in Resting Potential stage, it implies that a signal has aleady been propagated through the net



Running Instructions

1. Enter the number of Neurons. User is prompted to enter at least 50. However, any valid number will be accepted.

2. A random neural net is generated with initial states displayed in the table.

3. Enter the time steps after which change is to be observed. On pressing Enter, the new states of all Neurons are seen in the table.


Reference used for Potential Values inside Neuron

http://www.dummies.com/how-to/content/understanding-the-transmission-of-nerve-impulses.html