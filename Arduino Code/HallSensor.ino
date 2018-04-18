const int HallSensor[] = {5, 6, 7};

void setup() {
  Serial.begin(9600);
}
 
void loop() {
    int hallVal[] = {0, 0, 0};
    hallVal[0] = digitalRead(HallSensor[0]);
    hallVal[1] = digitalRead(HallSensor[1]);
    hallVal[2] = digitalRead(HallSensor[2]);

    if (hallVal[0] + hallVal[1] + hallVal[2] < 2){
      Serial.print("1");
    }else{
      Serial.print("0");
    }
    delay(1000);
}

