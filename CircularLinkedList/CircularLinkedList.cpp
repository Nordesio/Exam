#include <iostream>
#include <string>

class CircularLinkedList {
private:
	struct Node {
		int data;
		Node* next;

		Node(int val) : data(val), next(nullptr) {}
	};

	Node* tail;

public:
	CircularLinkedList() : tail(nullptr) {}

	void add(int data) {
		Node* newNode = new Node(data);
		if (!tail) {
			tail = newNode;
			tail->next = tail;
		}
		else {
			newNode->next = tail->next;
			tail->next = newNode;
			tail = newNode;
		}
	}

	void del(int key) {
		if (!tail) return;

		Node* current = tail->next;
		Node* prev = tail;
		do {
			if (current->data == key) {
				if (current == tail->next && current == tail) {
					delete current;
					tail = nullptr;
					return;
				}
				if (current == tail->next) {
					tail->next = current->next;
				}
				else if (current == tail) {
					prev->next = tail->next;
					tail = prev;
				}
				else {
					prev->next = current->next;
				}

				delete current;
				return;
			}
			prev = current;
			current = current->next;
		} while (current != tail->next);
	}

	void print() {
		if (!tail) {
			std::cout << "Empty!" << std::endl;
			return;
		}

		Node* current = tail->next;
		do {
			std::cout << " " << current->data;
			current = current->next;
		} while (current != tail->next);
		std::cout << std::endl;
	}

	void userInput() {
		char choice;
		do {
			int tailNumber;
			std::string type, model;

			std::cout << "Enter value to add: ";
			std::cin >> tailNumber;
			std::cin.ignore();

			add(tailNumber);

			std::cout << "Do you want to add another node? (y/n): ";
			std::cin >> choice;
		} while (choice == 'y' || choice == 'Y');
	}


	void userDelete() {
		char choice;
		do {
			int tailNumber;

			std::cout << "Enter value to delete: ";
			std::cin >> tailNumber;
			std::cin.ignore();

			del(tailNumber);

			std::cout << "Do you want to delete another node? (y/n): ";
			std::cin >> choice;
		} while (choice == 'y' || choice == 'Y');
	}

	~CircularLinkedList() {
		if (!tail) return;

		Node* current = tail->next;
		Node* nextNode;
		while (current != tail->next) {
			nextNode = current->next;
			delete current;
			current = current->next;
		}
	}
};


int main() {
	CircularLinkedList cll;
	std::cout << "CircularLinkedList " << std::endl;
	cll.add(10);
	cll.add(20);
	cll.add(30);
	cll.add(40);

	std::cout << "Before: ";
	cll.print();

	cll.userInput();
	cll.userDelete();
	std::cout << "After: ";
	cll.print();

	return 0;
}