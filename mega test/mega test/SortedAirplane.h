#ifndef SORTEDAIRPLANE_H
#define SORTEDAIRPLANE_H

#include "Airplane.h"
#include<vector>

class SortedAirplane {
public:
	void addAirplane(Airplane airplane);
	void printList();
private:
	std::vector<Airplane> airplanes;
	std::vector<Airplane>::iterator findPlace(Airplane airplane);
};

#endif // !SORTEDAIRPLANE_H
