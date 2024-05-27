#ifndef AIRPLANE_H
#define AIRPLANE_H

#include<string>

class Airplane {
public:
	Airplane();
	Airplane(std::string model,  std::string planeNumber );

	void SetModel(std::string model);
	void SetPlaneNumber(std::string planeNumber);
	std::string GetModel() ;
	std::string GetPlaneNumber() ;


private:
	std::string model;
	std::string planeNumber;
};

#endif // !AIRPLANE_H
