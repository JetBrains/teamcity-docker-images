import os, sys, getopt
from argparse import ArgumentError
from collections import defaultdict
from matplotlib.collections import PolyCollection
import matplotlib.pyplot as plt
import matplotlib.ticker as ticker
import csv


class ImageStatistics:
    """
    Represents statistics for Docker Image.
    """
    def __init__(self, repo: str, tag: str, date: str, size: str):
        self.repo = repo
        self.tag = tag
        self.date = date.split('T')[0]
        # PEP 237: Essentially, long renamed to int. That is, there is only one built-in integral type, named int; but it behaves mostly like the old long type.
        # Convert bytes to MBs
        self.size = int(size) / 1000000

    def __str__(self) -> str:
        return f"{self.date}-{self.repo}-{self.tag}-{self.size}"


def __get_statistics_from_line(csv_line_elements: list) -> ImageStatistics:
    """
    Parses a specific line of log output into ImageStatistics instance.
    """
    # 4 - minimal amount of elements within valuable payload
    if len(csv_line_elements) < 4:
        return None
    return ImageStatistics(repo=csv_line_elements[0],tag=csv_line_elements[1], date=csv_line_elements[2], size=csv_line_elements[3])


# Return file path
def __get_image_statistics(file_path: str) -> list:
    """
    Returns collection of ImageStatistics instances parsed from given file path.
    """
    data = list()
    with open(file_path) as file:
        reader_obj = csv.reader(file)
        for line in reader_obj:
            data.append(__get_statistics_from_line(line)) 
    return data


def plot_image_sizes(repo_name: str, image_size_data: list) -> None:
 
    fig, ax = plt.subplots()
    ax.ticklabel_format(useOffset=False)
    x_values = []
    y_values = []
    for item in image_size_data:
        print(item)
        if not item:
            continue
        x_values.append(item.tag)
        y_values.append(item.size)

    fig, ax = plt.subplots()
    fig.autofmt_xdate(bottom=0.2, rotation=10, ha='right')
    ax.plot(x_values, y_values, marker='o', label=repo_name)


    plt.title(f"'{repo_name}' - Image Size Trend")
    plt.xlabel('Date Last Pushed')
    plt.xticks(rotation=50)
    ax.grid(True)
    plt.ylabel('Size, MBs')
    plt.legend(loc='upper left') 
    fig.tight_layout()
    plt.show()



def main(argv):
    try:
        # -- parse CLI options
        opts, args = getopt.getopt(argv, 'hd:p:i:', ['directory='])
        print(opts)
        if not opts:
            raise getopt.GetoptError('Not enough arguments.')
    except (getopt.GetoptError, ArgumentError) as e:
        print(f"{e} \n Usage: python3 visualize.py -d <source directory>")
        sys.exit(2)
    
    # -- actions based on CLI options
    usage = 'python3 visualize.py -d <source directory>'
    source_directory = ''
    for opt, arg in opts:
        if opt == '-h':
            print(usage)
            sys.exit()
        elif opt == '-d':
            source_directory = arg
   
    folder = os.fsencode(source_directory)
    for file in os.listdir(folder):
        filename_decoded = file.decode('utf-8')
        filepath = f"{source_directory}/{filename_decoded}"

        image_statistics_from_file = __get_image_statistics(filepath)
        plot_image_sizes(image_statistics_from_file[0].repo, image_statistics_from_file)

if __name__ == "__main__":
   main(sys.argv[1:])
