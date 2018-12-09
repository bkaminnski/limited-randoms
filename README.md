# Limited Randoms Generator

Generate random numbers:

- that sum up to a provided *total sum*,
- with provided *precision*,
- in a range <0; limit),
  - there might be multiple limits,
  - numbers need to be split evenly within limits,
  - limit needs to be treated as a weight (and not an absolute value),
- matching a limit sharply (hard limit type) or smoothly (interpolated limit type).

## Sharp limit

- total sum: 10000 
- number of values: 1000 
- precision: 2 
- limits: 1;8;1;6;1;2;1 
- limit type: SHARP_LIMIT

![Sharp limit](readme/sharp.png)

## Smooth limit

- total sum: 10000 
- number of values: 1000 
- precision: 2 
- limits: 1;8;1;6;1;2;1 
- limit type: SMOOTH_LIMIT 

![Smooth limit](readme/smooth.png)